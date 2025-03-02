package com.gerenciador_estoque_fluxo_caixa.controllers;

import com.gerenciador_estoque_fluxo_caixa.constantes.ConstantesMenuFluxoCaixa;
import com.gerenciador_estoque_fluxo_caixa.dtos.categorias.CategoriaResponseDTO;
import com.gerenciador_estoque_fluxo_caixa.model.domain.NotaFiscal;
import com.gerenciador_estoque_fluxo_caixa.model.entities.ItemVenda;
import com.gerenciador_estoque_fluxo_caixa.model.entities.Produto;
import com.gerenciador_estoque_fluxo_caixa.service.CategoriaService;
import com.gerenciador_estoque_fluxo_caixa.service.ItemVendaService;
import com.gerenciador_estoque_fluxo_caixa.service.ProdutoService;
import com.gerenciador_estoque_fluxo_caixa.service.VendaService;
import com.gerenciador_estoque_fluxo_caixa.ui.ValidaSenha;
import com.gerenciador_estoque_fluxo_caixa.ui.caixaController.*;
import com.gerenciador_estoque_fluxo_caixa.ui.fluxoDeCaixa.FluxoDeCaixaView;
import com.gerenciador_estoque_fluxo_caixa.ui.produtos.AlertasProdutoView;
import com.gerenciador_estoque_fluxo_caixa.ui.produtos.LeDadosProduto;
import com.gerenciador_estoque_fluxo_caixa.ui.produtos.PrintaProduto;
import com.gerenciador_estoque_fluxo_caixa.utils.AutenticadorDeSenha;

import java.util.HashSet;
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
                        //adicionaProduto(listaCompras);
                        break;

                    case (ConstantesMenuFluxoCaixa.SACOLA_COMPRAS):
                        //listarSacola(listaCompras);

                        break;

                    case (ConstantesMenuFluxoCaixa.PRODUTOS_EM_ESTOQUE):
                        //listarEstoque();

                        break;

                    case (ConstantesMenuFluxoCaixa.REMOVER_DA_SACOLA):

                        //removerProduto(listaCompras);

                        break;

                    case (ConstantesMenuFluxoCaixa.ALTERAR_QUANTIDADE):

                        //modificarQuantidade(listaCompras);

                        break;

                    case (ConstantesMenuFluxoCaixa.FINALIZAR_COMPRA):

                        //finalizarCompra(listaCompras, notaFiscal);
                        break;

                    case (ConstantesMenuFluxoCaixa.LIMPAR_SACOLA):

                        //limparCarrinho(listaCompras);
                        break;

                    case (ConstantesMenuFluxoCaixa.MENU_PRINCIPAL):

                        //opcaoMenuFluxoDeCaixa = sair();

                        break;

                }
            } catch (NumberFormatException erro) {
                MenuCaixaController.alertaEntradaInvalida();
            }

        } while (!opcaoMenuFluxoDeCaixa.equals(ConstantesMenuFluxoCaixa.MENU_PRINCIPAL));
    }


    private void adicionaProduto(Set<ItemVenda> listaCompras) {

        String codigoProduto = AdicionarProduto.leCodigoProduto();

        if (ItemVendaService.contemProduto(listaCompras, codigoProduto)) {

            Integer novaQuantidade = AdicionarProduto.leNovamenteQuantidade();

            ItemVenda prod = ItemVendaService.retornaItemVendaPeloCodigo(listaCompras, codigoProduto);

            Produto produtoEstoque = produtoService.retornaProdutoPorCodigo(codigoProduto);

            int quantidadeRealProduto = prod.getQuantidade() + produtoEstoque.getQuantidade();

            if (quantidadeRealProduto >= novaQuantidade) {

                prod.setQuantidade(novaQuantidade);
                produtoEstoque.setQuantidade(quantidadeRealProduto - novaQuantidade);

                produtoService.atualizaProduto(produtoEstoque);

            } else if (quantidadeRealProduto <= 0) {
                AdicionarProduto.alertaProdutoIndisponivel();
            } else {

                int opcao = AdicionarProduto.exibirDialogoConfirmacaoAdicionarItensRestantes();

                if (opcao == 0) {

                    prod.setQuantidade(quantidadeRealProduto);
                    produtoEstoque.setQuantidade(0);

                    produtoService.atualizaProduto(produtoEstoque);

                } else {
                    AdicionarProduto.alertaCompraProdutoCancelada();
                }
            }

        } else {

            Produto produto = produtoService.retornaProdutoPorCodigo(codigoProduto);

            if (produto != null) {
                Integer quantidade = LeDadosProduto.leQuantidadeProduto();

                if (produto.getQuantidade() >= quantidade) {

                    ItemVenda item = new ItemVenda(produto, quantidade);

                    listaCompras.add(item);

                    produto.setQuantidade(produto.getQuantidade() - quantidade);

                    produtoService.atualizaProduto(produto);

                } else if (produto.getQuantidade() == 0) {
                    AdicionarProduto.alertaProdutoSemEstoque();
                } else {

                    int opcao = AdicionarProduto.exibirDialogoConfirmacaoAdicionarItensRestantes();

                    if (opcao == 0) {

                        ItemVenda item = new ItemVenda(produto, produto.getQuantidade());

                        listaCompras.add(item);

                        produto.setQuantidade(0);
                        produtoService.atualizaProduto(produto);

                    } else {
                        AdicionarProduto.alertaCompraProdutoCancelada();
                    }
                }
            } else {
                AdicionarProduto.alertaProdutoSemEstoque();

            }
        }
    }

    private void listarSacola(Set<ItemVenda> listaCompras) {

        String compras = ItemVendaService.geraRelatorioItemVenda(listaCompras);

        double subtotal = ItemVendaService.somaPrecos(listaCompras);
        String subtotalFormatado = String.format("Subtotal: %.2f R$", subtotal);
        String resultado = "Produtos da sacola de compras: \n" + compras + "\n" + subtotalFormatado;

        ListarSacola.listaSacola();

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

        if (listaCompras.isEmpty()) {
            RemoverProduto.alertaSacolaVazia();
        } else {
            String codigoProdutoParaRemover = LeDadosProduto.leCodigoBarraProduto();

            if (!ItemVendaService.contemProduto(listaCompras, codigoProdutoParaRemover)) {
                RemoverProduto.alertaProdutoJaNaoConstava();
            } else {

                ItemVenda ItemListaCompras = ItemVendaService.retornaItemVendaPeloCodigo(listaCompras,
                        codigoProdutoParaRemover);

                Produto produtoDoEstoque = produtoService.retornaProdutoPorCodigo(codigoProdutoParaRemover);
                int quantidadeRealProduto = ItemListaCompras.getQuantidade() + produtoDoEstoque.getQuantidade();

                produtoDoEstoque.setQuantidade(quantidadeRealProduto);

                produtoService.atualizaProduto(produtoDoEstoque);
                listaCompras.remove(ItemListaCompras);

                RemoverProduto.alertaProdutoRemovidoComSucesso();
            }
        }
    }

    private void modificarQuantidade(Set<ItemVenda> listaCompras) {
        if (listaCompras.isEmpty()) {
            ModificarQuantidade.alertaCarrinhoVazio();
        } else {
            String codigo = LeDadosProduto.leCodigoBarraProduto();

            if (ItemVendaService.contemProduto(listaCompras, codigo)) {
                Integer novaQuantidade = LeDadosProduto.leQuantidadeProduto();

                ItemVenda prod = ItemVendaService.retornaItemVendaPeloCodigo(listaCompras, codigo);

                Produto produtoEstoque = produtoService.retornaProdutoPorCodigo(codigo);

                int quantidadeRealProduto = prod.getQuantidade() + produtoEstoque.getQuantidade();

                if (quantidadeRealProduto >= novaQuantidade) {

                    prod.setQuantidade(novaQuantidade);
                    produtoEstoque.setQuantidade(quantidadeRealProduto - novaQuantidade);

                    produtoService.atualizaProduto(produtoEstoque);

                } else if (quantidadeRealProduto <= 0) {
                    ModificarQuantidade.alertaProdutoInvalido();
                } else {

                    int opcao = AdicionarProduto.exibirDialogoConfirmacaoAdicionarItensRestantes();

                    if (opcao == 0) {

                        prod.setQuantidade(quantidadeRealProduto);
                        produtoEstoque.setQuantidade(0);

                        produtoService.atualizaProduto(produtoEstoque);

                    } else {
                        AdicionarProduto.alertaCompraProdutoCancelada();
                    }
                }
                ModificarQuantidade.alertaQuantidadeProdutoModifica();
            } else {
                ModificarQuantidade.alertaProdutoInvalido();
            }
        }
    }

    private void finalizarCompra(Set<ItemVenda> listaCompras, NotaFiscal notaFiscal) {
        if (!listaCompras.isEmpty()) {

            Venda venda = new Venda();
            venda.setDataHora(LocalDateTime.now());

            vendaService.adicionaVenda(venda);

            Double total = 0.0;

            for (ItemVenda itemVenda : listaCompras) {
                itemVenda.setVenda(venda);
                total += itemVenda.subTotal();
                itemVendaService.adicionaItemVenda(itemVenda);
            }

            venda.setTotal(total);

            vendaService.atualizaVenda(venda);

            if (notaFiscal.getStatusNotaFiscal()) {
                GeradorNotaFiscal.geradorNotaFiscal(venda, listaCompras, notaFiscal.getCaminhoNotaFiscal());
            }

            listaCompras.clear();

            FinalizarCompra.mensagemAgracedimentoCompra();
        }
    }

    private void limparCarrinho(Set<ItemVenda> listaCompras) {
        if (!listaCompras.isEmpty()) {
            for (ItemVenda item : listaCompras) {

                Produto produtoEmEstoque = produtoService.retornaProdutoPorCodigo(item.getProduto().getCodigoDeBarra());

                int quantidadeReal = item.getQuantidade() + produtoEmEstoque.getQuantidade();

                produtoEmEstoque.setQuantidade(quantidadeReal);

                produtoService.atualizaProduto(produtoEmEstoque);

            }
            listaCompras.clear();

            LimparCarrinho.alertaSacolaLimpaSucesso();
        }
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

}
