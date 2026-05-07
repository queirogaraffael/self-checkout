package gerenciador.controller;

import gerenciador.constant.MenuEstoqueConstant;
import gerenciador.dto.categoria.CategoriaResponseDTO;
import gerenciador.dto.produto.ProdutoAtualizarPrecoDTO;
import gerenciador.dto.produto.ProdutoAtualizarQuantidadeDTO;
import gerenciador.dto.produto.ProdutoCreateDTO;
import gerenciador.dto.produto.ProdutoDTO;
import gerenciador.dto.venda.VendaResponseDTO;
import gerenciador.model.enums.StatusNotaFiscal;
import gerenciador.model.NotaFiscal;
import gerenciador.model.Categoria;
import gerenciador.model.ItemVenda;
import gerenciador.service.CategoriaService;
import gerenciador.service.ItemVendaService;
import gerenciador.service.ProdutoService;
import gerenciador.service.VendaService;
import gerenciador.service.NotaFiscalService;
import gerenciador.view.estoque.GerenciadorDeEstoqueView;
import gerenciador.view.estoque.notafiscal.NotaFiscalView;
import gerenciador.view.shared.categoria.CategoriasView;
import gerenciador.view.estoque.data.AlertasDataView;
import gerenciador.view.estoque.data.LeDataView;
import gerenciador.view.shared.produto.AlertasProdutoView;
import gerenciador.view.estoque.EditarProdutoView;
import gerenciador.view.shared.produto.LeDadosProdutoView;
import gerenciador.view.shared.produto.PrintaProdutoView;
import gerenciador.view.estoque.venda.AlertasVendaView;
import gerenciador.view.estoque.venda.LeDadosVendaView;
import gerenciador.view.estoque.venda.PrintarVendaView;
import gerenciador.view.estoque.venda.VendaView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public class EstoqueController {

    private final NotaFiscal notaFiscal;
    private final ItemVendaService itemVendaService;
    private final CategoriaService categoriaService;
    private final ProdutoService produtoService;
    private final VendaService vendaService;
    private final NotaFiscalService notaFiscalService = new NotaFiscalService();

    public EstoqueController(NotaFiscal notaFiscal,
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

    public void gerenciadorEstoque() {
        String opcao = "";
        do {
            try {
                opcao = GerenciadorDeEstoqueView.exibirMenuGerenciadorDeEstoque();

                switch (opcao) {

                    case (MenuEstoqueConstant.CADASTRAR):

                        cadastrarProduto();
                        break;

                    case (MenuEstoqueConstant.EDITAR):

                        editarProduto();
                        break;

                    case (MenuEstoqueConstant.LISTAGEM):
                        listarProdutos();
                        break;
                        
                    case (MenuEstoqueConstant.VISUALIZAR_PRODUTO):
                        visualizarProduto();
                        break;

                    case (MenuEstoqueConstant.LISTAGEM_ESTOQUE_BAIXO):
                        listaProdutosEstoqueBaixo();
                        break;

                    case (MenuEstoqueConstant.LISTAGEM_CATEGORIAS):
                        listarCategorias();
                        break;

                    case (MenuEstoqueConstant.CONFIGURAR_NOTA_FICAL):

                        ativadorNotaFiscal(notaFiscal);
                        break;

                    case (MenuEstoqueConstant.LISTAGEM_VENDAS):
                        listarVendas();
                        break;

                    case (MenuEstoqueConstant.DETALHES_VENDA):

                        detalharVenda();
                        break;

                    case (MenuEstoqueConstant.MENU_PRINCIPAL):
                        break;

                }
            } catch (NumberFormatException erro) {
                GerenciadorDeEstoqueView.alertaEntradasInvalida();
            }

        } while (!opcao.equals(MenuEstoqueConstant.MENU_PRINCIPAL));
    }

    private void visualizarProduto() {

        String codigo = LeDadosProdutoView.leCodigoBarraProduto();

        if (produtoService.haProdutoComMesmoCodigoBarra(codigo)){
            ProdutoDTO produto = produtoService.retornaProdutoDTO(codigo);

            PrintaProdutoView.printaProdutos(produto.toString());

        }else{
           AlertasProdutoView.alertaProdutoNaoEncontrado();
        }
        
    }

    private void cadastrarProduto() {

        String codigoBarra = LeDadosProdutoView.leCodigoBarraProduto();

        if (produtoService.haProdutoComMesmoCodigoBarra(codigoBarra)) {
            AlertasProdutoView.alertaProdutoJaCadastrado();
        } else {

            String nome = LeDadosProdutoView.leNomeProduto();
            BigDecimal valor = LeDadosProdutoView.leValorProduto();
            Integer quantidade = LeDadosProdutoView.leQuantidadeProduto();

            CategoriaResponseDTO categoria = selecionaCategoria();

            ProdutoCreateDTO produtoCreateDTO = produtoService.adicionaProduto(new ProdutoCreateDTO(codigoBarra, nome, valor, quantidade, new Categoria(categoria.getId(), categoria.getNome())));

            if (produtoCreateDTO != null) {
                PrintaProdutoView.printaProdutoCriado(produtoCreateDTO);
            }

        }
    }


    private void editarProduto() {

        int opcaoEditar = EditarProdutoView.opcaoEditar();

        if (opcaoEditar != 2) {
            String codigo = LeDadosProdutoView.leCodigoBarraProduto();

            if (produtoService.haProdutoComMesmoCodigoBarra(codigo)) {
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

                    statusAtualizacao = produtoService.atualizaQuantidadeProduto(codigo,atualizarQuantidadeDTO);
                }

                AlertasProdutoView.alertaAtualizacaoProduto(statusAtualizacao);
            } else {
                EditarProdutoView.alertaProdutoNaoCadastradoAinda();
            }

        }


    }

    private void listarProdutos() {

        if (produtoService.haProduto()) {
            AlertasProdutoView.alertaListaProdutoVazia();
        } else {
            CategoriaResponseDTO categoria = selecionaCategoria();

            int idCategoria = categoria.getId();

            String resultado = produtoService.geraRelatorioProdutosPorCategoria(idCategoria);

            PrintaProdutoView.printaProdutos(resultado);
        }

    }


    private void listaProdutosEstoqueBaixo() {

        String resultado = produtoService.geraRelatorioProdutosEstoqueBaixo();

        if (resultado.isEmpty()) {
            AlertasProdutoView.alertaProdutoEstoqueBaixo();
        } else {
            PrintaProdutoView.printaProdutos(resultado);
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
            AlertasVendaView.alertaSemVendaRegistrada();
            return;
        }

        int opcaoListagem = VendaView.listarVendasOpcoes();

        if (opcaoListagem == 0) {
            String resultadoListagemVendas = vendaService.retornaRelatorioVendas();
            PrintarVendaView.printarVenda(resultadoListagemVendas);

        } else if (opcaoListagem == 1) {
            String dataString = LeDataView.leData();

            try {
                String resultadoListagemVendasPorData = vendaService.retornaRelatorioVendasPorData(dataString);

                if (resultadoListagemVendasPorData.isEmpty()) {
                    AlertasVendaView.semResultadoVendaParaData();
                } else {
                    PrintarVendaView.printarVenda(resultadoListagemVendasPorData);
                }
            } catch (IllegalArgumentException e) {
                if ("FORMATO_INVALIDO".equals(e.getMessage())) {
                    AlertasDataView.alertaProblemaFormatoData();
                } else if ("DATA_FUTURA".equals(e.getMessage())) {
                    AlertasDataView.alertaDataPosteriorAtual();
                }
            }
        }
    }

    private void detalharVenda() {
        if (vendaService.haVenda()) {
            int codigo = LeDadosVendaView.leCodigoVenda();

            if (vendaService.haVendaComEsseCodigo(codigo)) {

                VendaResponseDTO vendaDTO = vendaService.retornaVenda(codigo);

                Set<ItemVenda> itens = itemVendaService.retornaItensVenda(codigo);

                String relatorioItensVenda = ItemVendaService.geraRelatorioItemVenda(itens);

                String resultado = vendaService.gerarResumoVenda(vendaDTO, relatorioItensVenda);

                PrintarVendaView.printarVenda(resultado);

            } else {
                AlertasVendaView.alertaVendaInvalida();
            }

        } else {
            AlertasVendaView.alertaSemVendaRegistrada();
        }
    }

    private CategoriaResponseDTO selecionaCategoria() {
        Object[] categorias = categoriaService.retornaCategorias();

        Object resultadoCategoria = CategoriasView.categoriaEscolhida(categorias);

        return categoriaService.converteResultadoParaCategoriaDTO(resultadoCategoria);
    }

}
