package gerenciador.view.estoque.venda;

import javax.swing.*;

public class LeDadosVendaView {

    public static int leCodigoVenda() {
        return Integer.parseInt(JOptionPane.showInputDialog("Codigo de venda: "));
    }

}
