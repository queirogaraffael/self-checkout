package main.java.gerenciador.estoque.caixa.ui.caixaController;

import javax.swing.*;

public class AdicionarProdutoUi {

    public static String leCodigoProduto(){
        return JOptionPane.showInputDialog("Digite o codigo do produto que voce deseja adicionar a lista de compras");
    }

    public static Integer leNovamenteQuantidade(){
        return Integer.parseInt(JOptionPane.showInputDialog(
                "Produto ja adicionado anteriormente, digite a nova quantidade que voce deseja: "));
    }

    public static void alertaProdutoIndisponivel(){
        JOptionPane.showMessageDialog(null, "Produto indisponivel. Tente outro!");
    }

    public static int exibirDialogoConfirmacaoAdicionarItensRestantes() {
        String[] opcoes = {"Sim", "Não"};
        return JOptionPane.showOptionDialog(
                null,
                "Deseja adicionar todos os itens restantes ?",
                "Quantidade desejada menor do que em estoque.",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );
    }

    public static void alertaCompraProdutoCancelada(){
        JOptionPane.showMessageDialog(null, "Compra de produto cancelada.");
    }

    public static void alertaProdutoSemEstoque(){
        JOptionPane.showMessageDialog(null, "Produto sem estoque. Tente outro!");
    }

}
