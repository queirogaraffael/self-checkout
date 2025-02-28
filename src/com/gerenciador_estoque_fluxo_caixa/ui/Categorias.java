package com.gerenciador_estoque_fluxo_caixa.ui;

import javax.swing.*;

public class Categorias {
    public static void exibirCategorias(Object[] categorias){
        JOptionPane.showMessageDialog(null, categorias);
    }

    public static Object categoriaEscolhida(Object[] categorias){
        return JOptionPane.showInputDialog(null, "Escolha uma categoria", "Categorias",
                JOptionPane.INFORMATION_MESSAGE, null, categorias, categorias[0]);
    }
}
