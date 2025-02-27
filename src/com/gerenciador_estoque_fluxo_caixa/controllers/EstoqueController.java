package com.gerenciador_estoque_fluxo_caixa.controllers;

import com.gerenciador_estoque_fluxo_caixa.constantes.ConstantesMenuEstoque;
import com.gerenciador_estoque_fluxo_caixa.dtos.produtos.ProdutoCreateDTO;
import com.gerenciador_estoque_fluxo_caixa.model.domain.NotaFiscal;
import com.gerenciador_estoque_fluxo_caixa.model.entities.ItemVenda;
import com.gerenciador_estoque_fluxo_caixa.model.entities.Produto;
import com.gerenciador_estoque_fluxo_caixa.model.entities.Venda;
import com.gerenciador_estoque_fluxo_caixa.service.CategoriaService;
import com.gerenciador_estoque_fluxo_caixa.service.ItemVendaService;
import com.gerenciador_estoque_fluxo_caixa.service.ProdutoService;
import com.gerenciador_estoque_fluxo_caixa.service.VendaService;
import com.gerenciador_estoque_fluxo_caixa.ui.GerenciadorDeEstoqueView;
import com.gerenciador_estoque_fluxo_caixa.utils.ManipulacaoData;
import com.gerenciador_estoque_fluxo_caixa.utils.VerificaDiretorio;

import javax.swing.*;
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

                    case (ConstantesMenuEstoque.REMOVER):

                        removerProduto();
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
                JOptionPane.showMessageDialog(null,
                        "Entrada invalida. Por favor, insira um numero correspondente a�op�ao desejada.");
            }

        } while (!opcao.equals(ConstantesMenuEstoque.MENU_PRINCIPAL));
    }

    private void cadastrarProduto() {

        String codigoBarra = JOptionPane.showInputDialog("Digite o codigo de barra do produto: ");

        if (produtoService.retornaProdutoPorCodigo(codigoBarra) != null) {
            JOptionPane.showMessageDialog(null, "Produto ja cadastrado anteriormente.");
        } else {

            String nome = JOptionPane.showInputDialog("Digite o nome do produto: ");
            Double valor = Double.parseDouble(JOptionPane.showInputDialog("Valor do produto: "));
            Integer quantidade = Integer.parseInt(JOptionPane.showInputDialog("Quantidade do produto: "));

            int categoria = categoriaService.retornaIdCategoria();

            produtoService.adicionaProduto(new ProdutoCreateDTO(codigoBarra, nome, valor, quantidade, categoria));

        }

    }

    private void editarProduto() {

        Object[] opcoes = {"Nome", "Preco", "Quantidade", "Categoria", "Voltar"};

        int opcaoEditar = JOptionPane.showOptionDialog(null, "Escolha uma opcao para modificar: ", "Modificar",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

        if (opcaoEditar != 4) {
            String codigo = JOptionPane.showInputDialog("Digite o codigo do produto: ");

            Produto produto = produtoService.retornaProdutoPorCodigo(codigo);

            if (produtoService.retornaProdutoPorCodigo(codigo) != null) {
                if (opcaoEditar == 0) {
                    String novoNome = JOptionPane.showInputDialog("Digite o novo nome: ");
                    produto.setNome(novoNome);
                    produtoService.atualizaProduto(produto);
                } else if (opcaoEditar == 1) {
                    Double novoPreco = Double.valueOf(JOptionPane.showInputDialog("Digite o novo preco: "));
                    produto.setPreco(novoPreco);
                    produtoService.atualizaProduto(produto);
                } else if (opcaoEditar == 2) {
                    Integer novaQuantidade = Integer
                            .parseInt(JOptionPane.showInputDialog("Digite a nova quantidade: "));
                    produto.setQuantidade(novaQuantidade);
                    produtoService.atualizaProduto(produto);
                } else if (opcaoEditar == 3) {
                    Integer novaCategoria = Integer.parseInt(JOptionPane.showInputDialog("Digite a nova categoria: "));
                    produto.setCategoria(novaCategoria);
                    produtoService.atualizaProduto(produto);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Produto nao cadastrado ainda. Tente outro!");
            }
        }

    }

    private void listarProdutos() {

        if (produtoService.tabelaProdutoEstaVazia()) {
            JOptionPane.showMessageDialog(null, "Lista de produtos vazia.");
        } else {

            int categoria = categoriaService.retornaIdCategoria();

            String resultado = produtoService.geraRelatotioProdutos(categoria);

            JOptionPane.showMessageDialog(null, resultado);
        }

    }

    private void listaProdutosEstoqueBaixo() {

        String resultado = produtoService.geraRelatorioProdutosEstoqueBaixo();

        if (resultado.equals("")) {
            JOptionPane.showMessageDialog(null, "Sem produtos com baixo estoque!");
        } else {
            JOptionPane.showMessageDialog(null, resultado);
        }

    }

    private void listarCategorias() {

        Object resultado = categoriaService.categorias();

        JOptionPane.showMessageDialog(null, resultado);

    }

    private void removerProduto() {

        String codigoProdutoParaRemover = JOptionPane
                .showInputDialog("Digite o codigo do produto que voce deseja remover:");

        produtoService.removeProduto(codigoProdutoParaRemover);
    }

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
}
