package com.gerenciador_estoque_fluxo_caixa.controllers;

import com.gerenciador_estoque_fluxo_caixa.constantes.ConstantesMenuEstoque;
import com.gerenciador_estoque_fluxo_caixa.dtos.categorias.CategoriaResponseDTO;
import com.gerenciador_estoque_fluxo_caixa.dtos.produtos.ProdutoAtualizarPrecoDTO;
import com.gerenciador_estoque_fluxo_caixa.dtos.produtos.ProdutoAtualizarQuantidadeDTO;
import com.gerenciador_estoque_fluxo_caixa.dtos.produtos.ProdutoCreateDTO;
import com.gerenciador_estoque_fluxo_caixa.dtos.vendas.VendaResponseDTO;
import com.gerenciador_estoque_fluxo_caixa.model.domain.NotaFiscal;
import com.gerenciador_estoque_fluxo_caixa.model.entities.Categoria;
import com.gerenciador_estoque_fluxo_caixa.model.entities.ItemVenda;
import com.gerenciador_estoque_fluxo_caixa.service.CategoriaService;
import com.gerenciador_estoque_fluxo_caixa.service.ItemVendaService;
import com.gerenciador_estoque_fluxo_caixa.service.ProdutoService;
import com.gerenciador_estoque_fluxo_caixa.service.VendaService;
import com.gerenciador_estoque_fluxo_caixa.ui.GerenciadorDeEstoqueView;
import com.gerenciador_estoque_fluxo_caixa.ui.NotaFiscalUI;
import com.gerenciador_estoque_fluxo_caixa.ui.categorias.Categorias;
import com.gerenciador_estoque_fluxo_caixa.ui.datas.AlertasData;
import com.gerenciador_estoque_fluxo_caixa.ui.datas.LeData;
import com.gerenciador_estoque_fluxo_caixa.ui.produtos.AlertasProdutoView;
import com.gerenciador_estoque_fluxo_caixa.ui.produtos.EditarProduto;
import com.gerenciador_estoque_fluxo_caixa.ui.produtos.LeDadosProduto;
import com.gerenciador_estoque_fluxo_caixa.ui.produtos.PrintaProduto;
import com.gerenciador_estoque_fluxo_caixa.ui.vendas.AlertasVenda;
import com.gerenciador_estoque_fluxo_caixa.ui.vendas.LeDadosVenda;
import com.gerenciador_estoque_fluxo_caixa.ui.vendas.PrintarVenda;
import com.gerenciador_estoque_fluxo_caixa.ui.vendas.VendaUI;
import com.gerenciador_estoque_fluxo_caixa.utils.ManipulacaoData;
import com.gerenciador_estoque_fluxo_caixa.utils.VerificaDiretorio;

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

    private void cadastrarProduto() {

        String codigoBarra = LeDadosProduto.leCodigoBarraProduto();

        if (produtoService.haProdutoComMesmoCodigoBarra(codigoBarra)) {
            AlertasProdutoView.alertaProdutoJaCadastrado();
        } else {

            String nome = LeDadosProduto.leNomeProduto();
            Double valor = LeDadosProduto.leValorProduto();
            Integer quantidade = LeDadosProduto.leQuantidadeProduto();

            CategoriaResponseDTO categoria = selecionaCategoria();

            ProdutoCreateDTO produtoCreateDTO = produtoService.adicionaProduto(new ProdutoCreateDTO(codigoBarra, nome, valor, quantidade, new Categoria(categoria.getId(), categoria.getNome())));

            if (produtoCreateDTO != null) {
                PrintaProduto.printaProdutoCriado(produtoCreateDTO);
            }

        }
    }


    private void editarProduto() {

        int opcaoEditar = EditarProduto.opcaoEditar();

        if (opcaoEditar != 2) {
            String codigo = LeDadosProduto.leCodigoBarraProduto();

            if (produtoService.haProdutoComMesmoCodigoBarra(codigo)) {
                boolean statusAtualizacao;

                if (opcaoEditar == 0) {
                    ProdutoAtualizarPrecoDTO atualizarPrecoDTO = new ProdutoAtualizarPrecoDTO();
                    Double novoPreco = EditarProduto.leNovoPreco();
                    atualizarPrecoDTO.setPreco(novoPreco);

                  statusAtualizacao = produtoService.atualizaPrecoProduto(codigo, atualizarPrecoDTO);

                } else {
                    ProdutoAtualizarQuantidadeDTO atualizarQuantidadeDTO = new ProdutoAtualizarQuantidadeDTO();
                    Integer novaQuantidade = EditarProduto.leNovaQuantidade();
                    atualizarQuantidadeDTO.setQuantidade(novaQuantidade);

                    statusAtualizacao = produtoService.atualizaQuantidadeProduto(codigo,atualizarQuantidadeDTO);
                }

                AlertasProdutoView.alertaAtualizacaoProduto(statusAtualizacao);
            } else {
                EditarProduto.alertaProdutoNaoCadastradoAinda();
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

            PrintaProduto.printaProdutos(resultado);
        }

    }


    private void listaProdutosEstoqueBaixo() {

        String resultado = produtoService.geraRelatorioProdutosEstoqueBaixo();

        if (resultado.isEmpty()) {
            AlertasProdutoView.alertaProdutoEstoqueBaixo();
        } else {
            PrintaProduto.printaProdutos(resultado);
        }

    }

    private void listarCategorias() {
        Object[] categorias = categoriaService.retornaCategorias();
        Categorias.exibirCategorias(categorias);
    }


    private void ativadorNotaFiscal(NotaFiscal notaFiscal) {
        String mensagem = NotaFiscalUI.verificarAcao(notaFiscal);

        int opcao = NotaFiscalUI.opcaoNotaFiscal(mensagem);

        if (opcao == 0) {
            String path = NotaFiscalUI.leCaminhoNotaFiscal();

            if (VerificaDiretorio.verificarDiretorio(path)) {
                notaFiscal.setCaminhoNotaFiscal(path);
                notaFiscal.setStatusNotaFiscal(true);

                String msg = NotaFiscalUI.mensagemSucesso(notaFiscal);
                NotaFiscalUI.printaMensagem(msg);
            } else {
                String msg = NotaFiscalUI.mensagemFalha(notaFiscal);
                NotaFiscalUI.printaMensagem(msg);
            }

        }

    }

    private void listarVendas() {

        if (!vendaService.haVenda()) {

            int opcaoListagem = VendaUI.listarVendasOpcoes();

            if (opcaoListagem == 0) {

                String resultadoListagemVendas = vendaService.retornaRelatorioVendas();
                PrintarVenda.printarVenda(resultadoListagemVendas);

            } else if (opcaoListagem == 1) {

                String dataString = LeData.leData();
                boolean formatoAprovado = ManipulacaoData.verificaFormatoData(dataString);

                if (formatoAprovado) {

                    LocalDate data = ManipulacaoData.retornaLocalDate(dataString);
                    if (!ManipulacaoData.verificaSeADataEPosterior(dataString)) {

                        String resultadoListagemVendasPorData = vendaService.retornaRelatorioVendasPorData(data);

                        if (resultadoListagemVendasPorData.isEmpty()) {
                            AlertasVenda.semResultadoVendaParaData();
                        } else {
                            PrintarVenda.printarVenda(resultadoListagemVendasPorData);
                        }

                    } else {
                        AlertasData.alertaDataPosteriorAtual();
                    }

                } else {
                    AlertasData.alertaProblemaFormatoData();
                }

            } else {
                AlertasVenda.alertaSemVendaRegistrada();
            }
        }
    }

    private void detalharVenda() {
        if (!vendaService.haVenda()) {
            int codigo = LeDadosVenda.leCodigoVenda();

            if (vendaService.haVendaComEsseCodigo(codigo)) {

                VendaResponseDTO vendaDTO = vendaService.retornaVenda(codigo);

                Set<ItemVenda> itens = itemVendaService.retornaItensVenda(codigo);

                String relatorioItensVenda = ItemVendaService.geraRelatorioItemVenda(itens);

                String resultado = vendaService.gerarResumoVenda(vendaDTO, relatorioItensVenda);

                PrintarVenda.printarVenda(resultado);

            } else {
                AlertasVenda.alertaVendaInvalida();
            }

        } else {
            AlertasVenda.alertaSemVendaRegistrada();
        }
    }

    private CategoriaResponseDTO selecionaCategoria() {
        Object[] categorias = categoriaService.retornaCategorias();

        Object resultadoCategoria = Categorias.categoriaEscolhida(categorias);

        return categoriaService.converteResultadoParaCategoriaDTO(resultadoCategoria);
    }

}
