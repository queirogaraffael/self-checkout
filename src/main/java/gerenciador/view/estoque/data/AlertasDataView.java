package gerenciador.view.estoque.data;

import javax.swing.*;

public class AlertasDataView {

    public static void alertaDataPosteriorAtual() {
        JOptionPane.showMessageDialog(null, "Data posterior a data atual. Tente novamente!");
    }

    public static void alertaProblemaFormatoData(){
        JOptionPane.showMessageDialog(null, "Problema no formato da data. Tente novamente!");
    }
}
