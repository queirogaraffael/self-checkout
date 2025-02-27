package com.gerenciador_estoque_fluxo_caixa.ui.produtos;

import javax.swing.*;

public class AlertasProdutoView {

    public static void alertaProdutoJaCadastrado(){
        JOptionPane.showMessageDialog(null, "Produto ja cadastrado anteriormente.");
    }

    public static void alertaProdutoCriadoComSucesso(){
        JOptionPane.showMessageDialog(null, "Produto criado com sucesso!");
    }
}
