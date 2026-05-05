package gerenciador.view.autoatendimento;

import javax.swing.*;

public class FinalizarCompraView {

    public static void mensagemAgracedimentoCompra(){
        JOptionPane.showMessageDialog(null, "Obrigado, volte sempre!");
    }

    public static void alertaProdutoEsgotado(String nomeProduto){
        JOptionPane.showMessageDialog(null, "\"" + nomeProduto + "\" está esgotado. Por favor, remova-o do carrinho e tente finalizar novamente.");
    }
}
