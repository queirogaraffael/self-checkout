package gerenciador.view.shared.produto;

import javax.swing.*;
import java.math.BigDecimal;

public class LeDadosProdutoView {

    public static String leCodigoBarraProduto(){
        return JOptionPane.showInputDialog("Digite o codigo de barra do produto: ");
    }

    public static String leNomeProduto(){
        return JOptionPane.showInputDialog("Digite o nome do produto: ");
    }

    public static BigDecimal leValorProduto(){
        return new BigDecimal(JOptionPane.showInputDialog("Valor do produto: "));
    }

    public static Integer leQuantidadeProduto(){
        return Integer.parseInt(JOptionPane.showInputDialog("Quantidade do produto: "));
    }

}
