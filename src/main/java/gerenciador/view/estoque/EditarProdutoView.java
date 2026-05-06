package gerenciador.view.estoque;

import javax.swing.*;
import java.math.BigDecimal;

public class EditarProdutoView {

    private static Object[] opcoes = {"Preco", "Quantidade", "Voltar"};

    public static int opcaoEditar() {
        return JOptionPane.showOptionDialog(null, "Escolha uma opcao para modificar: ", "Modificar",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
    }

    public static BigDecimal leNovoPreco() {
        return new BigDecimal(JOptionPane.showInputDialog("Digite o novo preco: "));
    }

    public static Integer leNovaQuantidade() {
        return Integer.parseInt(JOptionPane.showInputDialog("Digite a nova quantidade: "));
    }

    public static void alertaProdutoNaoCadastradoAinda() {
        JOptionPane.showMessageDialog(null, "Produto nao cadastrado ainda. Tente outro!");
    }

}
