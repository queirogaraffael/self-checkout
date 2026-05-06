package gerenciador.view.autoatendimento;

import javax.swing.*;
import java.math.BigDecimal;

public class ListarSacolaView {

    public static void exibirSacola(BigDecimal subtotal, String compras) {
        String subtotalFormatado = String.format("Subtotal: R$ %.2f", subtotal);
        String mensagem = "Produtos da sacola de compras:\n" + compras + "\n" + subtotalFormatado;

        JOptionPane.showMessageDialog(null, mensagem, "Sacola de Compras", JOptionPane.INFORMATION_MESSAGE);
    }

}
