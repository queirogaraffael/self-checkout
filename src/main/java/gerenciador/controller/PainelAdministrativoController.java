package gerenciador.controller;

import gerenciador.constant.MenuAdminConstant;
import gerenciador.dto.categoria.CategoriaResponseDTO;
import gerenciador.dto.produto.ProdutoAtualizarPrecoDTO;
import gerenciador.dto.produto.ProdutoAtualizarQuantidadeDTO;
import gerenciador.dto.produto.ProdutoCreateDTO;
import gerenciador.dto.produto.ProdutoDTO;
import gerenciador.config.NotaFiscal;
import gerenciador.model.ItemVenda;
import gerenciador.service.CategoriaService;
import gerenciador.service.ItemVendaService;
import gerenciador.service.ProdutoService;
import gerenciador.service.VendaService;
import gerenciador.service.NotaFiscalService;
import gerenciador.view.admin.PainelAdministrativoView;
import gerenciador.view.shared.AlertaGeralView;
import gerenciador.view.admin.notafiscal.NotaFiscalView;
import gerenciador.view.shared.categoria.CategoriasView;
import gerenciador.view.shared.produto.ProdutoView;
import gerenciador.view.admin.EditarProdutoView;
import gerenciador.view.admin.venda.VendaView;

import java.math.BigDecimal;
import java.util.Set;
import gerenciador.infrastructure.exception.ProdutoModificadoConcorrentementeException;

public class PainelAdministrativoController {

    private final NotaFiscal notaFiscal;
    private final ItemVendaService itemVendaService;
    private final CategoriaService categoriaService;
    private final ProdutoService produtoService;
    private final VendaService vendaService;
    private final NotaFiscalService notaFiscalService = new NotaFiscalService();

    public PainelAdministrativoController(NotaFiscal notaFiscal,
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

    public void exibirPainelAdministrativo() {
        String opcao = "";
        do {
            try {
                opcao = PainelAdministrativoView.exibirPainelAdministrativo();

                switch (opcao) {

                    case (MenuAdminConstant.CADASTRAR):

                        cadastrarProduto();
                        break;

                    case (MenuAdminConstant.EDITAR):

                        editarProduto();
                        break;

                    case (MenuAdminConstant.LISTAGEM):
                        listarProdutos();
                        break;

                    case (MenuAdminConstant.VISUALIZAR_PRODUTO):
                        visualizarProduto();
                        break;

                    case (MenuAdminConstant.LISTAGEM_ESTOQUE_BAIXO):
                        listaProdutosEstoqueBaixo();
                        break;

                    case (MenuAdminConstant.LISTAGEM_CATEGORIAS):
                        listarCategorias();
                        break;

                    case (MenuAdminConstant.CONFIGURAR_NOTA_FICAL):

                        ativadorNotaFiscal(notaFiscal);
                        break;

                    case (MenuAdminConstant.LISTAGEM_VENDAS):
                        listarVendas();
                        break;

                    case (MenuAdminConstant.DETALHES_VENDA):

                        detalharVenda();
                        break;

                    case (MenuAdminConstant.MENU_PRINCIPAL):
                        break;

                }
            } catch (NumberFormatException erro) {
                PainelAdministrativoView.alertaEntradasInvalida();
            } catch (Exception erro) {
                System.err.println("Erro inesperado no estoque: " + erro.getMessage());
                AlertaGeralView.alertaErroInesperado();
            }

        } while (!opcao.equals(MenuAdminConstant.MENU_PRINCIPAL));
    }

    private void visualizarProduto() {

        String codigo = ProdutoView.leCodigoBarraProduto();

        if (produtoService.haProdutoComMesmoCodigoBarra(codigo)) {
            ProdutoDTO produto = produtoService.retornaProdutoDTO(codigo);

            ProdutoView.printaProdutos(produto.toString());

        } else {
            ProdutoView.alertaProdutoNaoEncontrado();
        }

    }

    private void cadastrarProduto() {

        String codigoBarra = ProdutoView.leCodigoBarraProduto();

        if (produtoService.haProdutoComMesmoCodigoBarra(codigoBarra)) {
            ProdutoView.alertaProdutoJaCadastrado();
        } else {

            String nome = ProdutoView.leNomeProduto();
            BigDecimal valor = ProdutoView.leValorProduto();
            Integer quantidade = ProdutoView.leQuantidadeProduto();

            CategoriaResponseDTO categoria = selecionaCategoria();

            ProdutoCreateDTO produtoCreateDTO = produtoService.adicionaProduto(
                    new ProdutoCreateDTO(codigoBarra, nome, valor, quantidade, categoria.getId(), categoria.getNome()));

            if (produtoCreateDTO != null) {
                ProdutoView.printaProdutoCriado(produtoCreateDTO);
            }

        }
    }

    private void editarProduto() {

        int opcaoEditar = EditarProdutoView.opcaoEditar();

        if (opcaoEditar != 2) {
            String codigo = ProdutoView.leCodigoBarraProduto();

            if (produtoService.haProdutoComMesmoCodigoBarra(codigo)) {
                try {
                    boolean statusAtualizacao;

                    if (opcaoEditar == 0) {
                        ProdutoAtualizarPrecoDTO atualizarPrecoDTO = new ProdutoAtualizarPrecoDTO();
                        BigDecimal novoPreco = EditarProdutoView.leNovoPreco();
                        atualizarPrecoDTO.setPreco(novoPreco);

                        statusAtualizacao = produtoService.atualizaPrecoProduto(codigo, atualizarPrecoDTO);

                    } else {
                        ProdutoAtualizarQuantidadeDTO atualizarQuantidadeDTO = new ProdutoAtualizarQuantidadeDTO();
                        Integer novaQuantidade = EditarProdutoView.leNovaQuantidade();
                        atualizarQuantidadeDTO.setQuantidade(novaQuantidade);

                        statusAtualizacao = produtoService.atualizaQuantidadeProduto(codigo, atualizarQuantidadeDTO);
                    }

                    ProdutoView.alertaAtualizacaoProduto(statusAtualizacao);
                } catch (ProdutoModificadoConcorrentementeException e) {
                    ProdutoView.alertaConflitoAtualizacaoProduto();
                }
            } else {
                EditarProdutoView.alertaProdutoNaoCadastradoAinda();
            }

        }

    }

    private void listarProdutos() {

        if (produtoService.haProduto()) {
            ProdutoView.alertaListaProdutoVazia();
        } else {
            CategoriaResponseDTO categoria = selecionaCategoria();

            int idCategoria = categoria.getId();

            String resultado = produtoService.geraRelatorioProdutosPorCategoria(idCategoria);

            ProdutoView.printaProdutos(resultado);
        }

    }

    private void listaProdutosEstoqueBaixo() {

        String resultado = produtoService.geraRelatorioProdutosEstoqueBaixo();

        if (resultado.isEmpty()) {
            ProdutoView.alertaProdutoEstoqueBaixo();
        } else {
            ProdutoView.printaProdutos(resultado);
        }

    }

    private void listarCategorias() {
        Object[] categorias = categoriaService.retornaCategorias();
        CategoriasView.exibirCategorias(categorias);
    }

    private void ativadorNotaFiscal(NotaFiscal notaFiscal) {
        String mensagem = NotaFiscalView.verificarAcao(notaFiscal);

        int opcao = NotaFiscalView.opcaoNotaFiscal(mensagem);

        if (opcao == 0) {
            String path = NotaFiscalView.leCaminhoNotaFiscal();

            boolean sucesso = notaFiscalService.configurarCaminho(notaFiscal, path);

            if (sucesso) {
                String msg = NotaFiscalView.mensagemSucesso(notaFiscal);
                NotaFiscalView.printaMensagem(msg);
            } else {
                String msg = NotaFiscalView.mensagemFalha(notaFiscal);
                NotaFiscalView.printaMensagem(msg);
            }

        }

    }

    private void listarVendas() {

        if (!vendaService.haVenda()) {
            VendaView.alertaSemVendaRegistrada();
            return;
        }

        int opcaoListagem = VendaView.listarVendasOpcoes();

        if (opcaoListagem == 0) {
            String resultadoListagemVendas = vendaService.retornaRelatorioVendas();
            VendaView.printarVenda(resultadoListagemVendas);

        } else if (opcaoListagem == 1) {
            String dataString = VendaView.leData();

            try {
                String resultadoListagemVendasPorData = vendaService.retornaRelatorioVendasPorData(dataString);

                if (resultadoListagemVendasPorData.isEmpty()) {
                    VendaView.semResultadoVendaParaData();
                } else {
                    VendaView.printarVenda(resultadoListagemVendasPorData);
                }
            } catch (IllegalArgumentException e) {
                if ("FORMATO_INVALIDO".equals(e.getMessage())) {
                    VendaView.alertaProblemaFormatoData();
                } else if ("DATA_FUTURA".equals(e.getMessage())) {
                    VendaView.alertaDataPosteriorAtual();
                }
            }
        }
    }

    private void detalharVenda() {
        if (vendaService.haVenda()) {
            int codigo = VendaView.leCodigoVenda();

            if (vendaService.haVendaComEsseCodigo(codigo)) {

                Set<ItemVenda> itens = itemVendaService.retornaItensVenda(codigo);
                String resultado = vendaService.gerarDetalhesCompletosDaVenda(codigo, itens);
                VendaView.printarVenda(resultado);

            } else {
                VendaView.alertaVendaInvalida();
            }

        } else {
            VendaView.alertaSemVendaRegistrada();
        }
    }

    private CategoriaResponseDTO selecionaCategoria() {
        Object[] categorias = categoriaService.retornaCategorias();

        Object resultadoCategoria = CategoriasView.categoriaEscolhida(categorias);

        return categoriaService.converteResultadoParaCategoriaDTO(resultadoCategoria);
    }

}
