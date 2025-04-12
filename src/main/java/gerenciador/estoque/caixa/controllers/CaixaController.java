package main.java.gerenciador.estoque.caixa.controllers;


import main.java.gerenciador.estoque.caixa.constantes.ConstantesMenuFluxoCaixa;
import main.java.gerenciador.estoque.caixa.dtos.categorias.CategoriaResponseDTO;
import main.java.gerenciador.estoque.caixa.dtos.produtos.ProdutoAtualizarQuantidadeDTO;
import main.java.gerenciador.estoque.caixa.model.domain.NotaFiscal;
import main.java.gerenciador.estoque.caixa.model.entities.ItemVenda;
import main.java.gerenciador.estoque.caixa.model.entities.Venda;
import main.java.gerenciador.estoque.caixa.service.CategoriaService;
import main.java.gerenciador.estoque.caixa.service.ItemVendaService;
import main.java.gerenciador.estoque.caixa.service.ProdutoService;
import main.java.gerenciador.estoque.caixa.service.VendaService;
import main.java.gerenciador.estoque.caixa.ui.ValidaSenha;
import main.java.gerenciador.estoque.caixa.ui.caixaController.*;
import main.java.gerenciador.estoque.caixa.ui.categorias.CategoriasUI;
import main.java.gerenciador.estoque.caixa.ui.fluxoDeCaixa.FluxoDeCaixaUI;
import main.java.gerenciador.estoque.caixa.ui.produtos.AlertasProdutoUI;
import main.java.gerenciador.estoque.caixa.ui.produtos.LeDadosProdutoUI;
import main.java.gerenciador.estoque.caixa.ui.produtos.PrintaProdutoUI;
import main.java.gerenciador.estoque.caixa.utils.AutenticadorDeSenha;
import main.java.gerenciador.estoque.caixa.utils.GeradorNotaFiscal;

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
                opcaoMenuFluxoDeCaixa = FluxoDeCaixaUI.exibirMenuFluxoDeCaixa();

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
                MenuCaixaControllerUI.alertaEntradaInvalida();
            }

        } while (!opcaoMenuFluxoDeCaixa.equals(ConstantesMenuFluxoCaixa.MENU_PRINCIPAL));
    }


    private boolean processarAtualizacaoQuantidade(String codigoProduto, ItemVenda item, int quantidadeAtualNoCarrinho, int quantidadeDesejadaTotal) {
        ProdutoAtualizarQuantidadeDTO estoque = produtoService.retornaProdutoAtualizarQuantidadeDTO(codigoProduto);
        if (estoque == null) {
            AdicionarProdutoUi.alertaProdutoSemEstoque();
            return false;
        }

        int totalDisponivel = quantidadeAtualNoCarrinho + estoque.getQuantidade();

        if (quantidadeDesejadaTotal > totalDisponivel) {
            int opcao = AdicionarProdutoUi.exibirDialogoConfirmacaoAdicionarItensRestantes();
            if (opcao == 0) {
                quantidadeDesejadaTotal = totalDisponivel;
            } else {
                AdicionarProdutoUi.alertaCompraProdutoCancelada();
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
        String codigoProduto = AdicionarProdutoUi.leCodigoProduto();

        if (ItemVendaService.contemProduto(listaCompras, codigoProduto)) {
            ItemVenda itemVenda = ItemVendaService.retornaItemVendaPeloCodigo(listaCompras, codigoProduto);
            int quantidadeAtual = itemVenda.getQuantidade();
            int quantidadeAdicionar = LeDadosProdutoUI.leQuantidadeProduto();
            int novaQuantidade = quantidadeAtual + quantidadeAdicionar;
            processarAtualizacaoQuantidade(codigoProduto, itemVenda, quantidadeAtual, novaQuantidade);
        } else {
            int quantidade = LeDadosProdutoUI.leQuantidadeProduto();
            ItemVenda item = itemVendaService.criaItemVendaPorCodigoProduto(codigoProduto, 0);
            if (processarAtualizacaoQuantidade(codigoProduto, item, 0, quantidade)) {
                listaCompras.add(item);
            }
        }
    }

    private void modificarQuantidade(Set<ItemVenda> listaCompras) {
        if (listaCompras.isEmpty()) {
            ModificarQuantidadeUI.alertaCarrinhoVazio();
            return;
        }

        String codigo = LeDadosProdutoUI.leCodigoBarraProduto();
        if (!ItemVendaService.contemProduto(listaCompras, codigo)) {
            ModificarQuantidadeUI.alertaProdutoInvalido();
            return;
        }

        ItemVenda itemVenda = ItemVendaService.retornaItemVendaPeloCodigo(listaCompras, codigo);
        int quantidadeAtual = itemVenda.getQuantidade();
        Integer novaQuantidade = LeDadosProdutoUI.leQuantidadeProduto();

        if (novaQuantidade <= 0) {
            ModificarQuantidadeUI.alertaProdutoInvalido();
            return;
        }

        if (processarAtualizacaoQuantidade(codigo, itemVenda, quantidadeAtual, novaQuantidade)) {
            ModificarQuantidadeUI.alertaQuantidadeProdutoModifica();
        }
    }


    private void listarSacola(Set<ItemVenda> listaCompras) {
        double subtotal = ItemVendaService.somaPrecos(listaCompras);

        String relatorioListaCompras = ItemVendaService.geraRelatorioItemVenda(listaCompras);

        ListarSacolaUi.exibirSacola(subtotal, relatorioListaCompras);

    }


    private void listarEstoque() {

        if (produtoService.haProduto()) {
            AlertasProdutoUI.alertaListaProdutoVazia();
        } else {
            CategoriaResponseDTO categoria = selecionaCategoria();

            int idCategoria = categoria.getId();

            String resultado = produtoService.geraRelatorioProdutosPorCategoria(idCategoria);

            PrintaProdutoUI.printaProdutos(resultado);
        }

    }

    private void removerProduto(Set<ItemVenda> listaCompras) {

        if (Objects.isNull(listaCompras) || listaCompras.isEmpty()) {
            RemoverProdutoUI.alertaSacolaVazia();
            return;
        }

        String codigoProduto = LeDadosProdutoUI.leCodigoBarraProduto();

        if (!ItemVendaService.contemProduto(listaCompras, codigoProduto)) {
            RemoverProdutoUI.alertaProdutoJaNaoConstava();
            return;
        }

        ItemVenda itemVenda = ItemVendaService.retornaItemVendaPeloCodigo(listaCompras, codigoProduto);
        ProdutoAtualizarQuantidadeDTO produtoEstoque = produtoService.retornaProdutoAtualizarQuantidadeDTO(codigoProduto);

        assert itemVenda != null;
        produtoEstoque.setQuantidade(produtoEstoque.getQuantidade() + itemVenda.getQuantidade());
        produtoService.atualizaQuantidadeProduto(codigoProduto, produtoEstoque);
        listaCompras.remove(itemVenda);

        RemoverProdutoUI.alertaProdutoRemovidoComSucesso();
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
        FinalizarCompraUi.mensagemAgracedimentoCompra();
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
        LimparCarrinhoUi.alertaSacolaLimpaSucesso();
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

        Object resultadoCategoria = CategoriasUI.categoriaEscolhida(categorias);

        return categoriaService.converteResultadoParaCategoriaDTO(resultadoCategoria);
    }


}
