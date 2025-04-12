package main.java.gerenciador.estoque.caixa.ui.datas;


import main.java.gerenciador.estoque.caixa.utils.ManipulacaoData;

import javax.swing.*;

public class LeDataUI {

    public static String leData(){
        return JOptionPane.showInputDialog("Digite uma data no formato " + ManipulacaoData.formatoData);
    }
}
