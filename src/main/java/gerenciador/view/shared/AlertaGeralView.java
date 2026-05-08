package gerenciador.view.shared;

import javax.swing.*;

public class AlertaGeralView {

    public static void alertaErroInesperado() {
        JOptionPane.showMessageDialog(null,
                "Ocorreu um erro inesperado. Tente novamente.",
                "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
