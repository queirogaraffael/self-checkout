package gerenciador.controller;


import gerenciador.constant.MenuAutoatendimentoConstant;
import gerenciador.model.enums.StatusNotaFiscal;
import gerenciador.model.NotaFiscal;
import gerenciador.model.ItemVenda;
import gerenciador.model.Venda;
import gerenciador.service.ItemVendaService;
import gerenciador.service.ProdutoService;
import gerenciador.service.VendaService;
import gerenciador.view.menu.ValidaSenhaView;
import gerenciador.view.autoatendimento.*;
import gerenciador.view.autoatendimento.TerminalAutoatendimentoView;
import gerenciador.view.shared.produto.LeDadosProdutoView;
import gerenciador.util.AutenticadorDeSenha;
import gerenciador.service.GeradorNotaFiscal;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class AutoatendimentoController {

    private final NotaFiscal notaFiscal;
    private final ItemVendaService itemVendaService;
    private final ProdutoService produtoService;
    private final VendaService vendaService;

    public AutoatendimentoController(NotaFiscal notaFiscal,
                           ItemVendaService itemVendaService,
                           ProdutoService produtoService,
                           VendaService vendaService) {
        this.notaFiscal = notaFiscal;
        this.itemVendaService = itemVendaService;
        this.produtoService = produtoService;
        this.vendaService = vendaService;
    }

    public void fluxoDeCaixa() {

        String opcaoMenuFluxoDeCaixa = "";

        Set<ItemVenda> listaCompras = new HashSet<>();

        do {
            try {
                opcaoMenuFluxoDeCaixa = TerminalAutoatendimentoView.exibirMenuPrincipal();

                switch (opcaoMenuFluxoDeCaixa) {

                    case (MenuAutoatendimentoConstant.ADICIONAR_PRODUTO):
                        adicionaProduto(listaCompras);
                        break;

                    case (MenuAutoatendimentoConstant.SACOLA_COMPRAS):
                        listarSacola(listaCompras);
                        break;

                    case (MenuAutoatendimentoConstant.REMOVER_DA_SACOLA):
                        removerProduto(listaCompras);
                        break;

                    case (MenuAutoatendimentoConstant.CORRIGIR_QUANTIDADE):
                        corrigirQuantidade(listaCompras);
                        break;

                    case (MenuAutoatendimentoConstant.FINALIZAR_COMPRA):
                        finalizarCompra(listaCompras, notaFiscal);
                        break;

                    case (MenuAutoatendimentoConstant.LIMPAR_SACOLA):
                        limparCarrinho(listaCompras);
                        break;

                    case (MenuAutoatendimentoConstant.MENU_PRINCIPAL):
                        opcaoMenuFluxoDeCaixa = sair();
                        break;

                }
            } catch (NumberFormatException erro) {
                MenuAutoatendimentoView.alertaEntradaInvalida();
            }

        } while (!opcaoMenuFluxoDeCaixa.equals(MenuAutoatendimentoConstant.MENU_PRINCIPAL));
    }

    private void adicionaProduto(Set<ItemVenda> listaCompras) {
        String codigoProduto = AdicionarProdutoView.leCodigoProduto();

        if (!produtoService.existeProdutoPorCodigo(codigoProduto)) {
            AdicionarProdutoView.alertaProdutoIndisponivel();
            return;
        }

        int quantidade = LeDadosProdutoView.leQuantidadeProduto();

        if (ItemVendaService.contemProduto(listaCompras, codigoProduto)) {
            ItemVenda itemVenda = ItemVendaService.retornaItemVendaPeloCodigo(listaCompras, codigoProduto);
            itemVenda.setQuantidade(itemVenda.getQuantidade() + quantidade);
        } else {
            ItemVenda item = itemVendaService.criaItemVendaPorCodigoProduto(codigoProduto, quantidade);
            listaCompras.add(item);
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
        listaCompras.remove(itemVenda);
        RemoverProdutoView.alertaProdutoRemovidoComSucesso();
    }

    private void corrigirQuantidade(Set<ItemVenda> listaCompras) {
        if (listaCompras.isEmpty()) {
            CorrigirQuantidadeView.alertaCarrinhoVazio();
            return;
        }

        String codigo = LeDadosProdutoView.leCodigoBarraProduto();
        if (!ItemVendaService.contemProduto(listaCompras, codigo)) {
            CorrigirQuantidadeView.alertaProdutoInvalido();
            return;
        }

        ItemVenda itemVenda = ItemVendaService.retornaItemVendaPeloCodigo(listaCompras, codigo);
        Integer novaQuantidade = LeDadosProdutoView.leQuantidadeProduto();

        if (novaQuantidade <= 0) {
            CorrigirQuantidadeView.alertaProdutoInvalido();
            return;
        }

        itemVenda.setQuantidade(novaQuantidade);
        CorrigirQuantidadeView.alertaQuantidadeProdutoCorrigida();
    }

    private void listarSacola(Set<ItemVenda> listaCompras) {
        double subtotal = ItemVendaService.somaPrecos(listaCompras);
        String relatorioListaCompras = ItemVendaService.geraRelatorioItemVenda(listaCompras);
        ListarSacolaView.exibirSacola(subtotal, relatorioListaCompras);
    }

    private void finalizarCompra(Set<ItemVenda> listaCompras, NotaFiscal notaFiscal) {
        if (Objects.isNull(listaCompras) || listaCompras.isEmpty()) {
            return;
        }

        List<ItemVenda> itensDecrementados = new ArrayList<>();

        for (ItemVenda item : listaCompras) {
            boolean sucesso = decrementaComRetry(item, itensDecrementados);
            if (!sucesso) {
                FinalizarCompraView.alertaProdutoEsgotado(item.getProduto().getNome());
                rollbackEstoque(itensDecrementados);
                return;
            }
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

    private boolean decrementaComRetry(ItemVenda item, List<ItemVenda> itensDecrementados) {
        String codigo = item.getProduto().getCodigoDeBarra();
        int quantidade = item.getQuantidade();

        try {
            produtoService.decrementaEstoque(codigo, quantidade);
            itensDecrementados.add(item);
            return true;
        } catch (OptimisticLockException e) {
            int estoqueAtual = produtoService.retornaQuantidadeAtual(codigo);
            if (estoqueAtual >= quantidade) {
                produtoService.decrementaEstoque(codigo, quantidade);
                itensDecrementados.add(item);
                return true;
            }
            return false;
        }
    }

    private void rollbackEstoque(List<ItemVenda> itensDecrementados) {
        for (ItemVenda item : itensDecrementados) {
            produtoService.incrementaEstoque(item.getProduto().getCodigoDeBarra(), item.getQuantidade());
        }
    }

    private void limparCarrinho(Set<ItemVenda> listaCompras) {
        if (listaCompras == null || listaCompras.isEmpty()) {
            return;
        }
        listaCompras.clear();
        LimparCarrinhoView.alertaSacolaLimpaSucesso();
    }

    private String sair() {
        String senhaDigitada = ValidaSenhaView.exibirValidaSenha();
        boolean autenticacao = AutenticadorDeSenha.autenticacaoSenha(senhaDigitada);

        if (!autenticacao) {
            ValidaSenhaView.exibirSenhaIncorreta();
            return MenuAutoatendimentoConstant.CONTINUAR_NO_PROGRAMA;
        }
        return MenuAutoatendimentoConstant.MENU_PRINCIPAL;
    }

}
