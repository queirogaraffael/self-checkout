package main.java.gerenciador.estoque.caixa.controllers;

import main.java.gerenciador.estoque.caixa.constantes.ConstantesMenuEstoque;
import main.java.gerenciador.estoque.caixa.dtos.categorias.CategoriaResponseDTO;
import main.java.gerenciador.estoque.caixa.dtos.produtos.ProdutoAtualizarPrecoDTO;
import main.java.gerenciador.estoque.caixa.dtos.produtos.ProdutoAtualizarQuantidadeDTO;
import main.java.gerenciador.estoque.caixa.dtos.produtos.ProdutoCreateDTO;
import main.java.gerenciador.estoque.caixa.dtos.produtos.ProdutoDTO;
import main.java.gerenciador.estoque.caixa.dtos.vendas.VendaResponseDTO;
import main.java.gerenciador.estoque.caixa.enums.StatusNotaFiscal;
import main.java.gerenciador.estoque.caixa.model.domain.NotaFiscal;
import main.java.gerenciador.estoque.caixa.model.entities.Categoria;
import main.java.gerenciador.estoque.caixa.model.entities.ItemVenda;
import main.java.gerenciador.estoque.caixa.service.CategoriaService;
import main.java.gerenciador.estoque.caixa.service.ItemVendaService;
import main.java.gerenciador.estoque.caixa.service.ProdutoService;
import main.java.gerenciador.estoque.caixa.service.VendaService;
import main.java.gerenciador.estoque.caixa.ui.GerenciadorDeEstoqueView;
import main.java.gerenciador.estoque.caixa.ui.NotaFiscalUI;
import main.java.gerenciador.estoque.caixa.ui.categorias.CategoriasUI;
import main.java.gerenciador.estoque.caixa.ui.datas.AlertasDataUI;
import main.java.gerenciador.estoque.caixa.ui.datas.LeDataUI;
import main.java.gerenciador.estoque.caixa.ui.produtos.AlertasProdutoUI;
import main.java.gerenciador.estoque.caixa.ui.produtos.EditarProdutoUI;
import main.java.gerenciador.estoque.caixa.ui.produtos.LeDadosProdutoUI;
import main.java.gerenciador.estoque.caixa.ui.produtos.PrintaProdutoUI;
import main.java.gerenciador.estoque.caixa.ui.vendas.AlertasVendaUI;
import main.java.gerenciador.estoque.caixa.ui.vendas.LeDadosVendaUI;
import main.java.gerenciador.estoque.caixa.ui.vendas.PrintarVendaUI;
import main.java.gerenciador.estoque.caixa.ui.vendas.VendaUI;
import main.java.gerenciador.estoque.caixa.utils.ManipulacaoData;
import main.java.gerenciador.estoque.caixa.utils.VerificaDiretorio;

import java.time.LocalDate;
import java.util.Set;

public class EstoqueController {

    private final NotaFiscal notaFiscal;
    private final ItemVendaService itemVendaService;
    private final CategoriaService categoriaService;
    private final ProdutoService produtoService;
    private final VendaService vendaService;

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

                    case (ConstantesMenuEstoque.CADASTRAR):

                        cadastrarProduto();
                        break;

                    case (ConstantesMenuEstoque.EDITAR):

                        editarProduto();
                        break;

                    case (ConstantesMenuEstoque.LISTAGEM):
                        listarProdutos();
                        break;
                        
                    case (ConstantesMenuEstoque.VISUALIZAR_PRODUTO):
                        visualizarProduto();
                        break;

                    case (ConstantesMenuEstoque.LISTAGEM_ESTOQUE_BAIXO):
                        listaProdutosEstoqueBaixo();
                        break;

                    case (ConstantesMenuEstoque.LISTAGEM_CATEGORIAS):
                        listarCategorias();
                        break;

                    case (ConstantesMenuEstoque.CONFIGURAR_NOTA_FICAL):

                        ativadorNotaFiscal(notaFiscal);
                        break;

                    case (ConstantesMenuEstoque.LISTAGEM_VENDAS):
                        listarVendas();
                        break;

                    case (ConstantesMenuEstoque.DETALHES_VENDA):

                        detalharVenda();
                        break;

                    case (ConstantesMenuEstoque.MENU_PRINCIPAL):
                        break;

                }
            } catch (NumberFormatException erro) {
                GerenciadorDeEstoqueView.alertaEntradasInvalida();
            }

        } while (!opcao.equals(ConstantesMenuEstoque.MENU_PRINCIPAL));
    }

    private void visualizarProduto() {

        String codigo = LeDadosProdutoUI.leCodigoBarraProduto();

        if (produtoService.haProdutoComMesmoCodigoBarra(codigo)){
            ProdutoDTO produto = produtoService.retornaProdutoDTO(codigo);

            PrintaProdutoUI.printaProdutos(produto.toString());

        }else{
           AlertasProdutoUI.alertaProdutoNaoEncontrado();
        }
        
    }

    private void cadastrarProduto() {

        String codigoBarra = LeDadosProdutoUI.leCodigoBarraProduto();

        if (produtoService.haProdutoComMesmoCodigoBarra(codigoBarra)) {
            AlertasProdutoUI.alertaProdutoJaCadastrado();
        } else {

            String nome = LeDadosProdutoUI.leNomeProduto();
            Double valor = LeDadosProdutoUI.leValorProduto();
            Integer quantidade = LeDadosProdutoUI.leQuantidadeProduto();

            CategoriaResponseDTO categoria = selecionaCategoria();

            ProdutoCreateDTO produtoCreateDTO = produtoService.adicionaProduto(new ProdutoCreateDTO(codigoBarra, nome, valor, quantidade, new Categoria(categoria.getId(), categoria.getNome())));

            if (produtoCreateDTO != null) {
                PrintaProdutoUI.printaProdutoCriado(produtoCreateDTO);
            }

        }
    }


    private void editarProduto() {

        int opcaoEditar = EditarProdutoUI.opcaoEditar();

        if (opcaoEditar != 2) {
            String codigo = LeDadosProdutoUI.leCodigoBarraProduto();

            if (produtoService.haProdutoComMesmoCodigoBarra(codigo)) {
                boolean statusAtualizacao;

                if (opcaoEditar == 0) {
                    ProdutoAtualizarPrecoDTO atualizarPrecoDTO = new ProdutoAtualizarPrecoDTO();
                    Double novoPreco = EditarProdutoUI.leNovoPreco();
                    atualizarPrecoDTO.setPreco(novoPreco);

                  statusAtualizacao = produtoService.atualizaPrecoProduto(codigo, atualizarPrecoDTO);

                } else {
                    ProdutoAtualizarQuantidadeDTO atualizarQuantidadeDTO = new ProdutoAtualizarQuantidadeDTO();
                    Integer novaQuantidade = EditarProdutoUI.leNovaQuantidade();
                    atualizarQuantidadeDTO.setQuantidade(novaQuantidade);

                    statusAtualizacao = produtoService.atualizaQuantidadeProduto(codigo,atualizarQuantidadeDTO);
                }

                AlertasProdutoUI.alertaAtualizacaoProduto(statusAtualizacao);
            } else {
                EditarProdutoUI.alertaProdutoNaoCadastradoAinda();
            }

        }


    }

    private void listarProdutos() {

        if (produtoService.haProduto()) {
            AlertasProdutoUI.alertaListaProdutoVazia();
        } else {
            CategoriaResponseDTO categoria = selecionaCategoria();

            int idCategoria = categoria.getId();

            String resultado = produtoService.geraRelatorioProdutosPorCategoria(idCategoria);

            PrintaProdutoUI.printaProdutos(resultado);
        }

    }


    private void listaProdutosEstoqueBaixo() {

        String resultado = produtoService.geraRelatorioProdutosEstoqueBaixo();

        if (resultado.isEmpty()) {
            AlertasProdutoUI.alertaProdutoEstoqueBaixo();
        } else {
            PrintaProdutoUI.printaProdutos(resultado);
        }

    }

    private void listarCategorias() {
        Object[] categorias = categoriaService.retornaCategorias();
        CategoriasUI.exibirCategorias(categorias);
    }


    private void ativadorNotaFiscal(NotaFiscal notaFiscal) {
        String mensagem = NotaFiscalUI.verificarAcao(notaFiscal);

        int opcao = NotaFiscalUI.opcaoNotaFiscal(mensagem);

        if (opcao == 0) {
            String path = NotaFiscalUI.leCaminhoNotaFiscal();

            if (VerificaDiretorio.verificarDiretorio(path)) {
                notaFiscal.setCaminhoNotaFiscal(path);
                notaFiscal.setStatusNotaFiscal(StatusNotaFiscal.ATIVADA);

                String msg = NotaFiscalUI.mensagemSucesso(notaFiscal);
                NotaFiscalUI.printaMensagem(msg);
            } else {
                String msg = NotaFiscalUI.mensagemFalha(notaFiscal);
                NotaFiscalUI.printaMensagem(msg);
            }

        }

    }

    private void listarVendas() {

        if (vendaService.haVenda()) {

            int opcaoListagem = VendaUI.listarVendasOpcoes();

            if (opcaoListagem == 0) {

                String resultadoListagemVendas = vendaService.retornaRelatorioVendas();
                PrintarVendaUI.printarVenda(resultadoListagemVendas);

            } else if (opcaoListagem == 1) {

                String dataString = LeDataUI.leData();
                boolean formatoAprovado = ManipulacaoData.verificaFormatoData(dataString);

                if (formatoAprovado) {

                    LocalDate data = ManipulacaoData.retornaLocalDate(dataString);
                    if (!ManipulacaoData.verificaSeADataEPosterior(dataString)) {

                        String resultadoListagemVendasPorData = vendaService.retornaRelatorioVendasPorData(data);

                        if (resultadoListagemVendasPorData.isEmpty()) {
                            AlertasVendaUI.semResultadoVendaParaData();
                        } else {
                            PrintarVendaUI.printarVenda(resultadoListagemVendasPorData);
                        }

                    } else {
                        AlertasDataUI.alertaDataPosteriorAtual();
                    }

                } else {
                    AlertasDataUI.alertaProblemaFormatoData();
                }

            } else {
                AlertasVendaUI.alertaSemVendaRegistrada();
            }
        }
    }

    private void detalharVenda() {
        if (vendaService.haVenda()) {
            int codigo = LeDadosVendaUI.leCodigoVenda();

            if (vendaService.haVendaComEsseCodigo(codigo)) {

                VendaResponseDTO vendaDTO = vendaService.retornaVenda(codigo);

                Set<ItemVenda> itens = itemVendaService.retornaItensVenda(codigo);

                String relatorioItensVenda = ItemVendaService.geraRelatorioItemVenda(itens);

                String resultado = vendaService.gerarResumoVenda(vendaDTO, relatorioItensVenda);

                PrintarVendaUI.printarVenda(resultado);

            } else {
                AlertasVendaUI.alertaVendaInvalida();
            }

        } else {
            AlertasVendaUI.alertaSemVendaRegistrada();
        }
    }

    private CategoriaResponseDTO selecionaCategoria() {
        Object[] categorias = categoriaService.retornaCategorias();

        Object resultadoCategoria = CategoriasUI.categoriaEscolhida(categorias);

        return categoriaService.converteResultadoParaCategoriaDTO(resultadoCategoria);
    }

}
