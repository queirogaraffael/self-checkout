package gerenciador.view.estoque.venda;

import javax.swing.*;

public class AlertasVendaView {

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
