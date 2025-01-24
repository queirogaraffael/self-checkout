package com.gerenciador_estoque_fluxo_caixa.views;

import javax.swing.*;

public class ValidaSenha {

    public static String exibirValidaSenha() {
        return JOptionPane.showInputDialog(null, "Digite a senha", "Validação de Senha", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void exibirSenhaIncorreta() {
        JOptionPane.showMessageDialog(null, "Sennha incorreta", "Validação de Senha", JOptionPane.INFORMATION_MESSAGE);
    }

}
