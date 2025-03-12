package com.gerenciador_estoque_fluxo_caixa.ui.produtos;

import javax.swing.*;

public class EditarProdutoUI {

    private static Object[] opcoes = {"Preco", "Quantidade", "Voltar"};

    public static int opcaoEditar() {
        return JOptionPane.showOptionDialog(null, "Escolha uma opcao para modificar: ", "Modificar",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
    }

    public static Double leNovoPreco() {
        return Double.valueOf(JOptionPane.showInputDialog("Digite o novo preco: "));
    }

    public static Integer leNovaQuantidade() {
        return Integer.parseInt(JOptionPane.showInputDialog("Digite a nova quantidade: "));
    }

    public static void alertaProdutoNaoCadastradoAinda() {
        JOptionPane.showMessageDialog(null, "Produto nao cadastrado ainda. Tente outro!");
    }

}
