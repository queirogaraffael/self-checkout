package com.gerenciador_estoque_fluxo_caixa.ui.vendas;

import javax.swing.*;

public class LeDadosVendaUI {

    public static int leCodigoVenda() {
        return Integer.parseInt(JOptionPane.showInputDialog("Codigo de venda: "));
    }

}
