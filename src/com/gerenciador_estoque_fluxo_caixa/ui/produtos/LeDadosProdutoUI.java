package com.gerenciador_estoque_fluxo_caixa.ui.produtos;

import javax.swing.*;

public class LeDadosProdutoUI {

    public static String leCodigoBarraProduto(){
        return JOptionPane.showInputDialog("Digite o codigo de barra do produto: ");
    }

    public static String leNomeProduto(){
        return JOptionPane.showInputDialog("Digite o nome do produto: ");
    }

    public static Double leValorProduto(){
        return Double.parseDouble(JOptionPane.showInputDialog("Valor do produto: "));
    }

    public static Integer leQuantidadeProduto(){
        return Integer.parseInt(JOptionPane.showInputDialog("Quantidade do produto: "));
    }

}
