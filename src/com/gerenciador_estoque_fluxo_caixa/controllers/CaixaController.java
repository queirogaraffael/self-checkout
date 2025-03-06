package com.gerenciador_estoque_fluxo_caixa.controllers;

import com.gerenciador_estoque_fluxo_caixa.constantes.ConstantesMenuFluxoCaixa;
import com.gerenciador_estoque_fluxo_caixa.dtos.categorias.CategoriaResponseDTO;
import com.gerenciador_estoque_fluxo_caixa.dtos.produtos.ProdutoAtualizarQuantidadeDTO;
import com.gerenciador_estoque_fluxo_caixa.model.domain.NotaFiscal;
import com.gerenciador_estoque_fluxo_caixa.model.entities.ItemVenda;
import com.gerenciador_estoque_fluxo_caixa.model.entities.Venda;
import com.gerenciador_estoque_fluxo_caixa.service.CategoriaService;
import com.gerenciador_estoque_fluxo_caixa.service.ItemVendaService;
import com.gerenciador_estoque_fluxo_caixa.service.ProdutoService;
import com.gerenciador_estoque_fluxo_caixa.service.VendaService;
import com.gerenciador_estoque_fluxo_caixa.ui.ValidaSenha;
import com.gerenciador_estoque_fluxo_caixa.ui.caixaController.*;
import com.gerenciador_estoque_fluxo_caixa.ui.categorias.Categorias;
import com.gerenciador_estoque_fluxo_caixa.ui.fluxoDeCaixa.FluxoDeCaixaView;
import com.gerenciador_estoque_fluxo_caixa.ui.produtos.AlertasProdutoView;
import com.gerenciador_estoque_fluxo_caixa.ui.produtos.LeDadosProduto;
import com.gerenciador_estoque_fluxo_caixa.ui.produtos.PrintaProduto;
import com.gerenciador_estoque_fluxo_caixa.utils.AutenticadorDeSenha;
import com.gerenciador_estoque_fluxo_caixa.utils.GeradorNotaFiscal;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CaixaController {

    private final NotaFiscal notaFiscal;
    private final CategoriaService categoriaService;
    private final ItemVendaService itemVendaService;
    private final ProdutoService produtoService;
    private final VendaService vendaService;

    public CaixaController(NotaFiscal notaFiscal,
                           ItemVendaService itemVendaService,
                           CategoriaService categoriaService,
                           ProdutoService produtoService,
                           VendaService vendaService) {
        this.notaFiscal = notaFiscal;
        this.itemVendaService = itemVendaService;
        this.categoriaService = categoriaService;
        this.produtoService = produtoService;
        this.vendaService = vendaService;
    }

    public void fluxoDeCaixa() {

        String opcaoMenuFluxoDeCaixa = "";

        Set<ItemVenda> listaCompras = new HashSet<>();

        do {
            try {
                opcaoMenuFluxoDeCaixa = FluxoDeCaixaView.exibirMenuFluxoDeCaixa();

                switch (opcaoMenuFluxoDeCaixa) {

                    case (ConstantesMenuFluxoCaixa.ADICIONAR_PRODUTO):
                        adicionaProduto(listaCompras);
                        break;

                    case (ConstantesMenuFluxoCaixa.SACOLA_COMPRAS):
                        listarSacola(listaCompras);

                        break;

                    case (ConstantesMenuFluxoCaixa.PRODUTOS_EM_ESTOQUE):
                        listarEstoque();
                        break;

                    case (ConstantesMenuFluxoCaixa.REMOVER_DA_SACOLA):
                        removerProduto(listaCompras);
                        break;

                    case (ConstantesMenuFluxoCaixa.ALTERAR_QUANTIDADE):
                        modificarQuantidade(listaCompras);
                        break;

                    case (ConstantesMenuFluxoCaixa.FINALIZAR_COMPRA):
                        finalizarCompra(listaCompras, notaFiscal);
                        break;

                    case (ConstantesMenuFluxoCaixa.LIMPAR_SACOLA):
                        limparCarrinho(listaCompras);
                        break;

                    case (ConstantesMenuFluxoCaixa.MENU_PRINCIPAL):
                        opcaoMenuFluxoDeCaixa = sair();
                        break;

                }
            } catch (NumberFormatException erro) {
                MenuCaixaController.alertaEntradaInvalida();
            }

        } while (!opcaoMenuFluxoDeCaixa.equals(ConstantesMenuFluxoCaixa.MENU_PRINCIPAL));
    }


    private boolean processarAtualizacaoQuantidade(String codigoProduto, ItemVenda item, int quantidadeAtualNoCarrinho, int quantidadeDesejadaTotal) {
        ProdutoAtualizarQuantidadeDTO estoque = produtoService.retornaProdutoAtualizarQuantidadeDTO(codigoProduto);
        if (estoque == null) {
            AdicionarProduto.alertaProdutoSemEstoque();
            return false;
        }

        int totalDisponivel = quantidadeAtualNoCarrinho + estoque.getQuantidade();

        if (quantidadeDesejadaTotal > totalDisponivel) {
            int opcao = AdicionarProduto.exibirDialogoConfirmacaoAdicionarItensRestantes();
            if (opcao == 0) {
                quantidadeDesejadaTotal = totalDisponivel;
            } else {
                AdicionarProduto.alertaCompraProdutoCancelada();
                return false;
            }
        }

        if (item != null) {
            item.setQuantidade(quantidadeDesejadaTotal);
        }
        estoque.setQuantidade(totalDisponivel - quantidadeDesejadaTotal);
        produtoService.atualizaQuantidadeProduto(codigoProduto, estoque);
        return true;
    }


    private void adicionaProduto(Set<ItemVenda> listaCompras) {
        String codigoProduto = AdicionarProduto.leCodigoProduto();

        if (ItemVendaService.contemProduto(listaCompras, codigoProduto)) {
            ItemVenda itemVenda = ItemVendaService.retornaItemVendaPeloCodigo(listaCompras, codigoProduto);
            int quantidadeAtual = itemVenda.getQuantidade();
            int quantidadeAdicionar = LeDadosProduto.leQuantidadeProduto();
            int novaQuantidade = quantidadeAtual + quantidadeAdicionar;
            processarAtualizacaoQuantidade(codigoProduto, itemVenda, quantidadeAtual, novaQuantidade);
        } else {
            int quantidade = LeDadosProduto.leQuantidadeProduto();
            ItemVenda item = itemVendaService.criaItemVendaPorCodigoProduto(codigoProduto, 0);
            if (processarAtualizacaoQuantidade(codigoProduto, item, 0, quantidade)) {
                listaCompras.add(item);
            }
        }
    }

    private void modificarQuantidade(Set<ItemVenda> listaCompras) {
        if (listaCompras.isEmpty()) {
            ModificarQuantidade.alertaCarrinhoVazio();
            return;
        }

        String codigo = LeDadosProduto.leCodigoBarraProduto();
        if (!ItemVendaService.contemProduto(listaCompras, codigo)) {
            ModificarQuantidade.alertaProdutoInvalido();
            return;
        }

        ItemVenda itemVenda = ItemVendaService.retornaItemVendaPeloCodigo(listaCompras, codigo);
        int quantidadeAtual = itemVenda.getQuantidade();
        Integer novaQuantidade = LeDadosProduto.leQuantidadeProduto();

        if (novaQuantidade <= 0) {
            ModificarQuantidade.alertaProdutoInvalido();
            return;
        }

        if (processarAtualizacaoQuantidade(codigo, itemVenda, quantidadeAtual, novaQuantidade)) {
            ModificarQuantidade.alertaQuantidadeProdutoModifica();
        }
    }


    private void listarSacola(Set<ItemVenda> listaCompras) {
        double subtotal = ItemVendaService.somaPrecos(listaCompras);

        String relatorioListaCompras = ItemVendaService.geraRelatorioItemVenda(listaCompras );

        ListarSacola.exibirSacola(subtotal, relatorioListaCompras);

    }


    private void listarEstoque() {

        if (produtoService.haProduto()) {
            AlertasProdutoView.alertaListaProdutoVazia();
        } else {
            CategoriaResponseDTO categoria = selecionaCategoria();

            int idCategoria = categoria.getId();

            String resultado = produtoService.geraRelatorioProdutosPorCategoria(idCategoria);

            PrintaProduto.printaProdutos(resultado);
        }

    }

    private void removerProduto(Set<ItemVenda> listaCompras) {

        if (Objects.isNull(listaCompras) || listaCompras.isEmpty()) {
            RemoverProduto.alertaSacolaVazia();
            return;
        }

        String codigoProduto = LeDadosProduto.leCodigoBarraProduto();

        if (!ItemVendaService.contemProduto(listaCompras, codigoProduto)) {
            RemoverProduto.alertaProdutoJaNaoConstava();
            return;
        }

        ItemVenda itemVenda = ItemVendaService.retornaItemVendaPeloCodigo(listaCompras, codigoProduto);
        ProdutoAtualizarQuantidadeDTO produtoEstoque = produtoService.retornaProdutoAtualizarQuantidadeDTO(codigoProduto);

        assert itemVenda != null;
        produtoEstoque.setQuantidade(produtoEstoque.getQuantidade() + itemVenda.getQuantidade());
        produtoService.atualizaQuantidadeProduto(codigoProduto, produtoEstoque);
        listaCompras.remove(itemVenda);

        RemoverProduto.alertaProdutoRemovidoComSucesso();
    }


    private void finalizarCompra(Set<ItemVenda> listaCompras, NotaFiscal notaFiscal) {
        if (Objects.isNull(listaCompras) || listaCompras.isEmpty()) {
            return;
        }

        Venda venda = new Venda();
        venda.setDataHora(LocalDateTime.now());
        vendaService.adicionaVenda(venda);

        double total = listaCompras.stream()
                .peek(item -> item.setVenda(venda))
                .mapToDouble(ItemVenda::subTotal)
                .sum();

        listaCompras.forEach(itemVendaService::adicionaItemVenda);

        venda.setTotal(total);
        vendaService.atualizaVenda(venda);

        if (Boolean.TRUE.equals(notaFiscal.getStatusNotaFiscal())) {
            GeradorNotaFiscal.geradorNotaFiscal(venda, listaCompras, notaFiscal.getCaminhoNotaFiscal());
        }

        listaCompras.clear();
        FinalizarCompra.mensagemAgracedimentoCompra();
    }


    private void limparCarrinho(Set<ItemVenda> listaCompras) {

        if (listaCompras == null || listaCompras.isEmpty()) {
            return;
        }

        listaCompras.forEach(item -> {
            String codigo = item.getProduto().getCodigoDeBarra();

            ProdutoAtualizarQuantidadeDTO quantidadeEmEstoque = produtoService.retornaProdutoAtualizarQuantidadeDTO(codigo);
            quantidadeEmEstoque.setQuantidade(item.getQuantidade() + quantidadeEmEstoque.getQuantidade());

            produtoService.atualizaQuantidadeProduto(codigo, quantidadeEmEstoque);
        });

        listaCompras.clear();
        LimparCarrinho.alertaSacolaLimpaSucesso();
    }


    private String sair() {
        String senhaDigitada = ValidaSenha.exibirValidaSenha();
        boolean autenticacao = AutenticadorDeSenha.autenticacaoSenha(senhaDigitada);

        if (!autenticacao) {
            ValidaSenha.exibirSenhaIncorreta();
            return ConstantesMenuFluxoCaixa.CONTINUAR_NO_PROGRAMA;
        }
        return ConstantesMenuFluxoCaixa.MENU_PRINCIPAL;

    }

    private CategoriaResponseDTO selecionaCategoria() {
        Object[] categorias = categoriaService.retornaCategorias();

        Object resultadoCategoria = Categorias.categoriaEscolhida(categorias);

        return categoriaService.converteResultadoParaCategoriaDTO(resultadoCategoria);
    }


}
