package gerenciador.view.estoque;

import javax.swing.*;
import java.math.BigDecimal;

public class EditarProdutoView {

    private static Object[] opcoes = {"Atualizar Preço", "Adicionar/Remover Estoque", "Voltar"};

    public static int opcaoEditar() {
        return JOptionPane.showOptionDialog(null, "O que você deseja alterar neste produto?", "Modificar Produto",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
    }

    public static BigDecimal leNovoPreco() {
        return new BigDecimal(JOptionPane.showInputDialog("Informe o novo preço de venda (R$): "));
    }

    public static Integer leNovaQuantidade() {
        return Integer.parseInt(JOptionPane.showInputDialog("Informe a nova quantidade em estoque: "));
    }

    public static void alertaProdutoNaoCadastradoAinda() {
        JOptionPane.showMessageDialog(null, "Produto não encontrado. Verifique o código de barras e tente novamente.");
    }

}
