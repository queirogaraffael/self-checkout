package main.java.gerenciador.estoque.caixa.ui.caixaController;

import javax.swing.*;

public class ModificarQuantidadeUI {

    public static void alertaCarrinhoVazio(){
        JOptionPane.showMessageDialog(null, "Carrinho de compras vazio.");
    }

    public static void alertaQuantidadeProdutoModifica(){
        JOptionPane.showMessageDialog(null, "Quantidade modificada com sucesso!");
    }

    public static void alertaProdutoInvalido(){
        JOptionPane.showMessageDialog(null, "Produto invalido, tente outro!");
    }

}
