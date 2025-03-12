package com.gerenciador_estoque_fluxo_caixa.ui.vendas;

import javax.swing.*;

public class AlertasVendaUI {

    public static void semResultadoVendaParaData(){
        JOptionPane.showMessageDialog(null, "Sem resultado de vendas para esta data");
    }

    public static void alertaSemVendaRegistrada(){
        JOptionPane.showMessageDialog(null, "Sem venda registrada.");
    }

    public static void alertaVendaInvalida(){
        JOptionPane.showMessageDialog(null, "Venda invalida. Tente outra!");
    }

}
