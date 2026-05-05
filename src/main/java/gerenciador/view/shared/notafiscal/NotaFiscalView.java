package gerenciador.view.shared.notafiscal;

import javax.swing.JOptionPane;

public class NotaFiscalView {

    public static void alertaDiretorioNaoEncontrado(String caminho) {
        JOptionPane.showMessageDialog(null, "Diretorio não encontrado: " + caminho);
    }
}
