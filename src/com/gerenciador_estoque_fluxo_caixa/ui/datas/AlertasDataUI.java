package com.gerenciador_estoque_fluxo_caixa.ui.datas;

import javax.swing.*;

public class AlertasDataUI {

    public static void alertaDataPosteriorAtual() {
        JOptionPane.showMessageDialog(null, "Data posterior a data atual. Tente novamente!");
    }

    public static void alertaProblemaFormatoData(){
        JOptionPane.showMessageDialog(null, "Problema no formato da data. Tente novamente!");
    }
}
