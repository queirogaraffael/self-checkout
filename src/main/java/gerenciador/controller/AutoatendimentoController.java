package gerenciador.controller;


import gerenciador.constant.MenuAutoatendimentoConstant;
import gerenciador.model.enums.ResultadoFinalizacao;
import gerenciador.model.NotaFiscal;
import gerenciador.model.ItemVenda;
import gerenciador.service.FinalizarCompraService;
import gerenciador.service.ItemVendaService;
import gerenciador.service.ProdutoService;
import gerenciador.view.menu.ValidaSenhaView;
import gerenciador.view.autoatendimento.*;
import gerenciador.view.autoatendimento.TerminalAutoatendimentoView;
import gerenciador.view.shared.produto.LeDadosProdutoView;
import gerenciador.util.AutenticadorDeSenha;
import gerenciador.session.MonitorSessao;
import gerenciador.session.SessaoAutoatendimento;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class AutoatendimentoController {

    private final NotaFiscal notaFiscal;
    private final ItemVendaService itemVendaService;
    private final ProdutoService produtoService;
    private final FinalizarCompraService finalizarCompraService;

    public AutoatendimentoController(NotaFiscal notaFiscal,
                           ItemVendaService itemVendaService,
                           ProdutoService produtoService,
                           FinalizarCompraService finalizarCompraService) {
        this.notaFiscal = notaFiscal;
        this.itemVendaService = itemVendaService;
        this.produtoService = produtoService;
        this.finalizarCompraService = finalizarCompraService;
    }

    public void fluxoDeCaixa() {

        String opcaoMenuFluxoDeCaixa = "";

        Set<ItemVenda> listaCompras = new HashSet<>();
        
        SessaoAutoatendimento sessao = new SessaoAutoatendimento();
        MonitorSessao monitor = new MonitorSessao(sessao);

        monitor.iniciar(
            () -> listaCompras.isEmpty(),
            () -> {
                listaCompras.clear();
                sessao.setNoMenuAutoatendimento(true);
            }
        );

        do {
            sessao.setNoMenuAutoatendimento(true);
            try {
                opcaoMenuFluxoDeCaixa = TerminalAutoatendimentoView.exibirMenuPrincipal();
                sessao.registrarAtividade();
                
                if (opcaoMenuFluxoDeCaixa == null) {
                    opcaoMenuFluxoDeCaixa = "";
                } else if (!opcaoMenuFluxoDeCaixa.equals(MenuAutoatendimentoConstant.MENU_PRINCIPAL)) {
                    sessao.setNoMenuAutoatendimento(false);
                }

                switch (opcaoMenuFluxoDeCaixa) {

                    case (MenuAutoatendimentoConstant.ADICIONAR_PRODUTO):
                        adicionaProduto(listaCompras);
                        sessao.registrarAtividade();
                        break;

                    case (MenuAutoatendimentoConstant.SACOLA_COMPRAS):
                        listarSacola(listaCompras);
                        sessao.registrarAtividade();
                        break;

                    case (MenuAutoatendimentoConstant.REMOVER_DA_SACOLA):
                        removerProduto(listaCompras);
                        sessao.registrarAtividade();
                        break;

                    case (MenuAutoatendimentoConstant.CORRIGIR_QUANTIDADE):
                        corrigirQuantidade(listaCompras);
                        sessao.registrarAtividade();
                        break;

                    case (MenuAutoatendimentoConstant.FINALIZAR_COMPRA):
                        finalizarCompra(listaCompras);
                        sessao.registrarAtividade();
                        break;

                    case (MenuAutoatendimentoConstant.LIMPAR_SACOLA):
                        limparCarrinho(listaCompras);
                        sessao.registrarAtividade();
                        break;

                    case (MenuAutoatendimentoConstant.MENU_PRINCIPAL):
                        opcaoMenuFluxoDeCaixa = sair();
                        sessao.registrarAtividade();
                        break;

                }
            } catch (NumberFormatException erro) {
                MenuAutoatendimentoView.alertaEntradaInvalida();
            }

        } while (!MenuAutoatendimentoConstant.MENU_PRINCIPAL.equals(opcaoMenuFluxoDeCaixa));
        
        monitor.parar();
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
        BigDecimal subtotal = ItemVendaService.somaPrecos(listaCompras);
        String relatorioListaCompras = ItemVendaService.geraRelatorioItemVenda(listaCompras);
        ListarSacolaView.exibirSacola(subtotal, relatorioListaCompras);
    }

    private void finalizarCompra(Set<ItemVenda> listaCompras) {
        if (Objects.isNull(listaCompras) || listaCompras.isEmpty()) {
            return;
        }

        ResultadoFinalizacao resultado = finalizarCompraService.finalizar(listaCompras, notaFiscal);

        switch (resultado) {
            case SUCESSO:
                listaCompras.clear();
                FinalizarCompraView.mensagemAgracedimentoCompra();
                break;
            case PRODUTO_ESGOTADO:
                FinalizarCompraView.alertaProdutoEsgotado(resultado.getNomeProduto());
                break;
            case SISTEMA_OCUPADO:
                FinalizarCompraView.alertaSistemaSobrecarregado();
                break;
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
