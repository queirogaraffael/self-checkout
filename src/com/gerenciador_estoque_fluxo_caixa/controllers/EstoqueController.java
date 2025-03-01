package com.gerenciador_estoque_fluxo_caixa.controllers;

import com.gerenciador_estoque_fluxo_caixa.constantes.ConstantesMenuEstoque;
import com.gerenciador_estoque_fluxo_caixa.dtos.categorias.CategoriaResponseDTO;
import com.gerenciador_estoque_fluxo_caixa.dtos.produtos.ProdutoCreateDTO;
import com.gerenciador_estoque_fluxo_caixa.model.domain.NotaFiscal;
import com.gerenciador_estoque_fluxo_caixa.model.entities.Categoria;
import com.gerenciador_estoque_fluxo_caixa.service.CategoriaService;
import com.gerenciador_estoque_fluxo_caixa.service.ItemVendaService;
import com.gerenciador_estoque_fluxo_caixa.service.ProdutoService;
import com.gerenciador_estoque_fluxo_caixa.service.VendaService;
import com.gerenciador_estoque_fluxo_caixa.ui.Categorias;
import com.gerenciador_estoque_fluxo_caixa.ui.GerenciadorDeEstoqueView;
import com.gerenciador_estoque_fluxo_caixa.ui.produtos.AlertasProdutoView;
import com.gerenciador_estoque_fluxo_caixa.ui.produtos.EditarProduto;
import com.gerenciador_estoque_fluxo_caixa.ui.produtos.LeDadosProduto;
import com.gerenciador_estoque_fluxo_caixa.ui.produtos.PrintaProduto;


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

                        //editarProduto();
                        break;

                    case (ConstantesMenuEstoque.LISTAGEM):

                        // listarProdutos();
                        break;

                    case (ConstantesMenuEstoque.LISTAGEM_ESTOQUE_BAIXO):
                        //listaProdutosEstoqueBaixo();
                        break;

                    case (ConstantesMenuEstoque.LISTAGEM_CATEGORIAS):
                        listarCategorias();
                        break;

                    case (ConstantesMenuEstoque.CONFIGURAR_NOTA_FICAL):

                        // ativadorNotaFiscal(notaFiscal);
                        break;

                    case (ConstantesMenuEstoque.LISTAGEM_VENDAS):

                        //listarVendas();
                        break;

                    case (ConstantesMenuEstoque.DETALHES_VENDA):

                        // detalharVenda();
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
                PrintaProduto.exibeProdutoCriado(produtoCreateDTO);
            }

        }
    }


    private void editarProduto() {

        int opcaoEditar = EditarProduto.opcaoEditar();

        if (opcaoEditar != 2) {
            String codigo = LeDadosProduto.leCodigoBarraProduto();

            if (produtoService.haProdutoComMesmoCodigoBarra(codigo)) {

                ProdutoDTO produto = produtoService.retornaProdutoPorCodigo(codigo);


                if (opcaoEditar == 0) {
                    Double novoPreco = EditarProduto.leNovoPreco();
                    produto.setPreco(novoPreco);

                    produtoModificado = produtoService.atualizaProduto(produto);

                } else if (opcaoEditar == 1) {
                    Integer novaQuantidade = EditarProduto.leNovaQuantidade();
                    produto.setQuantidade(novaQuantidade);
                    produtoService.atualizaProduto(produto);
                }
            } else {
                EditarProduto.alertaProdutoNaoCadastradoAinda();
            }
        }

    }


    private void listarProdutos() {

        // utilizar um dto especifico(o mesmo que o de listar produtos com estoque baixo)

        if (produtoService.tabelaProdutoEstaVazia()) {
            AlertasProdutoView.alertaListaProdutoVazia();
        } else {

            // agora retorna a categoria em si
            // usa o metodo que retorna o DTO
            // parseia o id para integer
            int categoria = 2;

            // o relatorio deve passar a retornar uma lista de produtos DTO

            String resultado = produtoService.geraRelatotioProdutos(categoria);


            PrintaProduto.printaProdutos(resultado);
        }

    }

    private void listaProdutosEstoqueBaixo() {

        // criar um dto especifico

        // esse de gerar relatorio deve ta na parte de front
        String resultado = produtoService.geraRelatorioProdutosEstoqueBaixo();

        if (resultado.equals("")) {
            AlertasProdutoView.alertaProdutoEstoqueBaixo();
        } else {
            PrintaProduto.printaProdutos(resultado);
        }

    }

    private void listarCategorias() {
        Object[] categorias = categoriaService.retornaCategorias();
        Categorias.exibirCategorias(categorias);
    }

    /*

    // metodo para ver um unico produto

    private void ativadorNotaFiscal(NotaFiscal notaFiscal) {
        Object[] opcoes = {"Sim", "Nao"};

        String mensagem = notaFiscal.getStatusNotaFiscal() ? "Deseja modificar o diret�rio?"
                : "Ativar gerador de nota fiscal ?";

        int opcao = JOptionPane.showOptionDialog(null, mensagem, "Opcoes", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

        if (opcao == 0) {
            String path = JOptionPane.showInputDialog("Caminho do diretorio: ");

            if (VerificaDiretorio.verificarDiretorio(path)) {

                notaFiscal.setCaminhoNotaFiscal(path);
                notaFiscal.setStatusNotaFiscal(true);

                String msg = notaFiscal.getStatusNotaFiscal()
                        ? "Gerador de notas fiscais com novo diret�rio ativado com sucesso!"
                        : "Gerador de notas fiscais ativado com sucesso!";
                JOptionPane.showMessageDialog(null, msg);
            } else {

                String msg = notaFiscal.getStatusNotaFiscal()
                        ? "Falha ao tentar ativar o novo diretorio do gerador de notas fiscais."
                        : "Falha ao tentar ativar gerador de notas fiscais.";
                JOptionPane.showMessageDialog(null, msg);

            }

        }

    }

    private void listarVendas() {

        if (!vendaService.tabelaVendaEstaVazia()) {

            Object[] opcoes = {"Listar todas as vendas", "Listar venda por data especifica", "Voltar"};

            int opcaoListagem = JOptionPane.showOptionDialog(null, "Escolha uma opcao: ", "Listagem de vendas",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

            if (opcaoListagem == 0) {

                String resultadoListagemVendas = vendaService.geraRelatioVendas();
                JOptionPane.showMessageDialog(null, resultadoListagemVendas);

            } else if (opcaoListagem == 1) {
                String formatoData = "dd/MM/yyyy";
                String dataString = JOptionPane.showInputDialog("Digite uma data no formato dd/MM/yyyy");

                boolean formatoAprovado = ManipulacaoData.verificaFormatoData(dataString, formatoData);

                if (formatoAprovado) {

                    LocalDate data = ManipulacaoData.retornaLocalDate(dataString);
                    if (!ManipulacaoData.verificaSeADataEPosterior(dataString)) {

                        String resultadoListagemVendasPorData = vendaService.geraRelatiorioVendasPorData(data);

                        if (resultadoListagemVendasPorData.equals("")) {
                            JOptionPane.showMessageDialog(null, "Sem resultado de vendas para esta data");
                        } else {
                            JOptionPane.showMessageDialog(null, resultadoListagemVendasPorData);
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Data posterior a data atual. Tente novamente!");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Problema no formato da data. Tente novamente!");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Sem venda registrada.");
            }
        }
    }

    private void detalharVenda() {
        if (!vendaService.tabelaVendaEstaVazia()) {

            Integer codigo = Integer.parseInt(JOptionPane.showInputDialog("Codigo de venda: "));
            Venda venda = vendaService.retornaVendaPorCodigo(codigo);

            if (venda != null) {

                Set<ItemVenda> itens = itemVendaService.retornaItensVenda(venda);

                String itensVenda = ItemVendaService.geraRelatorioItemVenda(itens);

                String resultado = venda.toStringSemPreco() + "\n" + itensVenda
                        + String.format("Total: %.2f", venda.getTotal());

                JOptionPane.showMessageDialog(null, resultado);

            } else {
                JOptionPane.showMessageDialog(null, "Venda invalida. Tente outra!");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Sem venda registrada ainda.");
        }
    }
     */

    private CategoriaResponseDTO selecionaCategoria() {
        Object[] categorias = categoriaService.retornaCategorias();

        Object resultadoCategoria = Categorias.categoriaEscolhida(categorias);

        return categoriaService.converteResultadoParaCategoriaDTO(resultadoCategoria);
    }

}
