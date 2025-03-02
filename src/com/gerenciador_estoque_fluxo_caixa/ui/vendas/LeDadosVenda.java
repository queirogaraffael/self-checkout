package com.gerenciador_estoque_fluxo_caixa.ui.vendas;

import javax.swing.*;

public class LeDadosVenda {

    public static int leCodigoVenda() {
        return Integer.parseInt(JOptionPane.showInputDialog("Codigo de venda: "));
    }

}
