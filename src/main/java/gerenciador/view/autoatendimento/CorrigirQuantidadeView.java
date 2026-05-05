package gerenciador.view.autoatendimento;

import javax.swing.*;

public class CorrigirQuantidadeView {

    public static void alertaCarrinhoVazio(){
        JOptionPane.showMessageDialog(null, "Carrinho de compras vazio.");
    }

    public static void alertaQuantidadeProdutoCorrigida(){
        JOptionPane.showMessageDialog(null, "Quantidade corrigida com sucesso!");
    }

    public static void alertaProdutoInvalido(){
        JOptionPane.showMessageDialog(null, "Produto invalido, tente outro!");
    }

}
