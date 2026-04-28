package gerenciador.view.estoque.data;


import gerenciador.util.ManipulacaoData;

import javax.swing.*;

public class LeDataView {

    public static String leData(){
        return JOptionPane.showInputDialog("Digite uma data no formato " + ManipulacaoData.formatoData);
    }
}
