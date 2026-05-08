package gerenciador.controller;

import gerenciador.constant.MenuAutoatendimentoConstant;
import gerenciador.model.enums.ResultadoFinalizacao;
import gerenciador.config.NotaFiscal;
import gerenciador.model.ItemVenda;
import gerenciador.service.FinalizarCompraService;
import gerenciador.service.ItemVendaService;
import gerenciador.service.ProdutoService;
import gerenciador.view.menu.ValidaSenhaView;
import gerenciador.view.autoatendimento.AutoatendimentoView;
import gerenciador.view.autoatendimento.FinalizarCompraView;
import gerenciador.view.shared.produto.ProdutoView;
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
                });

        do {
            sessao.setNoMenuAutoatendimento(true);
            try {
                opcaoMenuFluxoDeCaixa = AutoatendimentoView.exibirMenuPrincipal();
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
                if (erro.getMessage() != null && erro.getMessage().equals("null")) {
                    continue;
                }
                AutoatendimentoView.alertaEntradaInvalida();
            }

        } while (!MenuAutoatendimentoConstant.MENU_PRINCIPAL.equals(opcaoMenuFluxoDeCaixa));

        monitor.parar();
    }

    private void adicionaProduto(Set<ItemVenda> listaCompras) {
        String codigoProduto = AutoatendimentoView.leCodigoProduto();

        if (codigoProduto == null)
            return;

        if (!produtoService.existeProdutoPorCodigo(codigoProduto)) {
            AutoatendimentoView.alertaProdutoIndisponivel();
            return;
        }

        int quantidade = ProdutoView.leQuantidadeProduto();

        itemVendaService.adicionarAoCarrinho(listaCompras, codigoProduto, quantidade);
    }

    private void removerProduto(Set<ItemVenda> listaCompras) {

        if (Objects.isNull(listaCompras) || listaCompras.isEmpty()) {
            AutoatendimentoView.alertaSacolaVazia();
            return;
        }

        String codigoProduto = ProdutoView.leCodigoBarraProduto();

        if (codigoProduto == null)
            return;

        if (!ItemVendaService.contemProduto(listaCompras, codigoProduto)) {
            AutoatendimentoView.alertaProdutoJaNaoConstava();
            return;
        }

        itemVendaService.removerDoCarrinho(listaCompras, codigoProduto);
        AutoatendimentoView.alertaProdutoRemovidoComSucesso();
    }

    private void corrigirQuantidade(Set<ItemVenda> listaCompras) {
        if (listaCompras.isEmpty()) {
            AutoatendimentoView.alertaCarrinhoVazio();
            return;
        }

        String codigo = ProdutoView.leCodigoBarraProduto();

        if (codigo == null)
            return;

        if (!ItemVendaService.contemProduto(listaCompras, codigo)) {
            AutoatendimentoView.alertaProdutoInvalido();
            return;
        }

        Integer novaQuantidade = ProdutoView.leQuantidadeProduto();

        boolean sucesso = itemVendaService.atualizarQuantidadeNoCarrinho(listaCompras, codigo, novaQuantidade);

        if (!sucesso) {
            AutoatendimentoView.alertaProdutoInvalido();
            return;
        }

        AutoatendimentoView.alertaQuantidadeProdutoCorrigida();
    }

    private void listarSacola(Set<ItemVenda> listaCompras) {
        BigDecimal subtotal = ItemVendaService.somaPrecos(listaCompras);
        String relatorioListaCompras = ItemVendaService.geraRelatorioItemVenda(listaCompras);
        AutoatendimentoView.exibirSacola(subtotal, relatorioListaCompras);
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
        AutoatendimentoView.alertaSacolaLimpaSucesso();
    }

    private String sair() {
        String senhaDigitada = ValidaSenhaView.exibirValidaSenha();

        if (senhaDigitada == null)
            return "";

        boolean autenticacao = AutenticadorDeSenha.autenticacaoSenha(senhaDigitada);

        if (!autenticacao) {
            ValidaSenhaView.exibirSenhaIncorreta();
            return MenuAutoatendimentoConstant.CONTINUAR_NO_PROGRAMA;
        }
        return MenuAutoatendimentoConstant.MENU_PRINCIPAL;
    }

}
