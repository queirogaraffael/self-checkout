package gerenciador.controller;


import gerenciador.constant.MenuFluxoCaixaConstant;
import gerenciador.dto.categoria.CategoriaResponseDTO;
import gerenciador.dto.produto.ProdutoAtualizarQuantidadeDTO;
import gerenciador.model.enums.StatusNotaFiscal;
import gerenciador.model.NotaFiscal;
import gerenciador.model.ItemVenda;
import gerenciador.model.Venda;
import gerenciador.service.CategoriaService;
import gerenciador.service.ItemVendaService;
import gerenciador.service.ProdutoService;
import gerenciador.service.VendaService;
import gerenciador.view.menu.ValidaSenhaView;
import gerenciador.view.caixa.*;
import gerenciador.view.shared.categoria.CategoriasView;
import gerenciador.view.caixa.FluxoDeCaixaView;
import gerenciador.view.shared.produto.AlertasProdutoView;
import gerenciador.view.shared.produto.LeDadosProdutoView;
import gerenciador.view.shared.produto.PrintaProdutoView;
import gerenciador.util.AutenticadorDeSenha;
import gerenciador.service.GeradorNotaFiscal;

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

                    case (MenuFluxoCaixaConstant.ADICIONAR_PRODUTO):
                        adicionaProduto(listaCompras);
                        break;

                    case (MenuFluxoCaixaConstant.SACOLA_COMPRAS):
                        listarSacola(listaCompras);

                        break;

                    case (MenuFluxoCaixaConstant.PRODUTOS_EM_ESTOQUE):
                        listarEstoque();
                        break;

                    case (MenuFluxoCaixaConstant.REMOVER_DA_SACOLA):
                        removerProduto(listaCompras);
                        break;

                    case (MenuFluxoCaixaConstant.ALTERAR_QUANTIDADE):
                        modificarQuantidade(listaCompras);
                        break;

                    case (MenuFluxoCaixaConstant.FINALIZAR_COMPRA):
                        finalizarCompra(listaCompras, notaFiscal);
                        break;

                    case (MenuFluxoCaixaConstant.LIMPAR_SACOLA):
                        limparCarrinho(listaCompras);
                        break;

                    case (MenuFluxoCaixaConstant.MENU_PRINCIPAL):
                        opcaoMenuFluxoDeCaixa = sair();
                        break;

                }
            } catch (NumberFormatException erro) {
                MenuCaixaView.alertaEntradaInvalida();
            }

        } while (!opcaoMenuFluxoDeCaixa.equals(MenuFluxoCaixaConstant.MENU_PRINCIPAL));
    }

    private boolean processarAtualizacaoQuantidade(String codigoProduto, ItemVenda item, int quantidadeAtualNoCarrinho, int quantidadeDesejadaTotal) {
        ProdutoAtualizarQuantidadeDTO estoque = produtoService.retornaProdutoAtualizarQuantidadeDTO(codigoProduto);
        if (estoque == null) {
            AdicionarProdutoView.alertaProdutoSemEstoque();
            return false;
        }

        int totalDisponivel = quantidadeAtualNoCarrinho + estoque.getQuantidade();

        if (quantidadeDesejadaTotal > totalDisponivel) {
            int opcao = AdicionarProdutoView.exibirDialogoConfirmacaoAdicionarItensRestantes();
            if (opcao == 0) {
                quantidadeDesejadaTotal = totalDisponivel;
            } else {
                AdicionarProdutoView.alertaCompraProdutoCancelada();
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
        String codigoProduto = AdicionarProdutoView.leCodigoProduto();

        if (ItemVendaService.contemProduto(listaCompras, codigoProduto)) {
            ItemVenda itemVenda = ItemVendaService.retornaItemVendaPeloCodigo(listaCompras, codigoProduto);
            int quantidadeAtual = itemVenda.getQuantidade();
            int quantidadeAdicionar = LeDadosProdutoView.leQuantidadeProduto();
            int novaQuantidade = quantidadeAtual + quantidadeAdicionar;
            processarAtualizacaoQuantidade(codigoProduto, itemVenda, quantidadeAtual, novaQuantidade);
        } else {
            int quantidade = LeDadosProdutoView.leQuantidadeProduto();
            ItemVenda item = itemVendaService.criaItemVendaPorCodigoProduto(codigoProduto, 0);
            if (processarAtualizacaoQuantidade(codigoProduto, item, 0, quantidade)) {
                listaCompras.add(item);
            }
        }
    }

    private void modificarQuantidade(Set<ItemVenda> listaCompras) {
        if (listaCompras.isEmpty()) {
            ModificarQuantidadeView.alertaCarrinhoVazio();
            return;
        }

        String codigo = LeDadosProdutoView.leCodigoBarraProduto();
        if (!ItemVendaService.contemProduto(listaCompras, codigo)) {
            ModificarQuantidadeView.alertaProdutoInvalido();
            return;
        }

        ItemVenda itemVenda = ItemVendaService.retornaItemVendaPeloCodigo(listaCompras, codigo);
        int quantidadeAtual = itemVenda.getQuantidade();
        Integer novaQuantidade = LeDadosProdutoView.leQuantidadeProduto();

        if (novaQuantidade <= 0) {
            ModificarQuantidadeView.alertaProdutoInvalido();
            return;
        }

        if (processarAtualizacaoQuantidade(codigo, itemVenda, quantidadeAtual, novaQuantidade)) {
            ModificarQuantidadeView.alertaQuantidadeProdutoModifica();
        }
    }

    private void listarSacola(Set<ItemVenda> listaCompras) {
        double subtotal = ItemVendaService.somaPrecos(listaCompras);

        String relatorioListaCompras = ItemVendaService.geraRelatorioItemVenda(listaCompras);

        ListarSacolaView.exibirSacola(subtotal, relatorioListaCompras);

    }

    private void listarEstoque() {

        if (produtoService.haProduto()) {
            AlertasProdutoView.alertaListaProdutoVazia();
        } else {
            CategoriaResponseDTO categoria = selecionaCategoria();

            int idCategoria = categoria.getId();

            String resultado = produtoService.geraRelatorioProdutosPorCategoria(idCategoria);

            PrintaProdutoView.printaProdutos(resultado);
        }

    }

    private void removerProduto(Set<ItemVenda> listaCompras) {

        if (Objects.isNull(listaCompras) || listaCompras.isEmpty()) {
            RemoverProdutoView.alertaSacolaVazia();
            return;
        }

        String codigoProduto = LeDadosProdutoView.leCodigoBarraProduto();

        if (!ItemVendaService.contemProduto(listaCompras, codigoProduto)) {
            RemoverProdutoView.alertaProdutoJaNaoConstava();
            return;
        }

        ItemVenda itemVenda = ItemVendaService.retornaItemVendaPeloCodigo(listaCompras, codigoProduto);
        ProdutoAtualizarQuantidadeDTO produtoEstoque = produtoService.retornaProdutoAtualizarQuantidadeDTO(codigoProduto);

        assert itemVenda != null;
        produtoEstoque.setQuantidade(produtoEstoque.getQuantidade() + itemVenda.getQuantidade());
        produtoService.atualizaQuantidadeProduto(codigoProduto, produtoEstoque);
        listaCompras.remove(itemVenda);

        RemoverProdutoView.alertaProdutoRemovidoComSucesso();
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

        if (notaFiscal.getStatusNotaFiscal() == StatusNotaFiscal.ATIVADA) {
            GeradorNotaFiscal.geradorNotaFiscal(venda, listaCompras, notaFiscal.getCaminhoNotaFiscal());
        }

        listaCompras.clear();
        FinalizarCompraView.mensagemAgracedimentoCompra();
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
        LimparCarrinhoView.alertaSacolaLimpaSucesso();
    }

    private String sair() {
        String senhaDigitada = ValidaSenhaView.exibirValidaSenha();
        boolean autenticacao = AutenticadorDeSenha.autenticacaoSenha(senhaDigitada);

        if (!autenticacao) {
            ValidaSenhaView.exibirSenhaIncorreta();
            return MenuFluxoCaixaConstant.CONTINUAR_NO_PROGRAMA;
        }
        return MenuFluxoCaixaConstant.MENU_PRINCIPAL;

    }

    private CategoriaResponseDTO selecionaCategoria() {
        Object[] categorias = categoriaService.retornaCategorias();

        Object resultadoCategoria = CategoriasView.categoriaEscolhida(categorias);

        return categoriaService.converteResultadoParaCategoriaDTO(resultadoCategoria);
    }


}
