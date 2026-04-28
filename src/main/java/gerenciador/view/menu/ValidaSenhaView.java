package gerenciador.view.menu;

import javax.swing.*;

public class ValidaSenhaView {

    public static String exibirValidaSenha() {
        return JOptionPane.showInputDialog(null, "Digite a senha", "Validação de Senha", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void exibirSenhaIncorreta() {
        JOptionPane.showMessageDialog(null, "Sennha incorreta", "Validação de Senha", JOptionPane.INFORMATION_MESSAGE);
    }

}
