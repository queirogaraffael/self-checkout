package com.gerenciador_estoque_fluxo_caixa.ui.datas;

import com.gerenciador_estoque_fluxo_caixa.utils.ManipulacaoData;

import javax.swing.*;

public class LeDataUI {

    public static String leData(){
        return JOptionPane.showInputDialog("Digite uma data no formato " + ManipulacaoData.formatoData);
    }
}
