package com.gerenciador_estoque_fluxo_caixa.ui.caixaController;

import javax.swing.*;

public class RemoverProdutoUI {

    public static void alertaSacolaVazia(){
        JOptionPane.showMessageDialog(null, "Adicione primeiro um produto a sua sacola para poder remover.");
    }

    public static void alertaProdutoRemovidoComSucesso(){
        JOptionPane.showMessageDialog(null, "Produto removida com sucesso!");
    }

    public static void alertaProdutoJaNaoConstava(){
        JOptionPane.showMessageDialog(null,
                "Produto ja nao constava na sacola. Tente novamente com uma produto existente.");

    }
}
