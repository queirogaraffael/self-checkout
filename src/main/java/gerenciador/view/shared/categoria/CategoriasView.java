package gerenciador.view.shared.categoria;

import javax.swing.*;

public class CategoriasView {
    public static void exibirCategorias(Object[] categorias){
        JOptionPane.showMessageDialog(null, categorias);
    }

    public static Object categoriaEscolhida(Object[] categorias){
        return JOptionPane.showInputDialog(null, "Escolha uma categoria", "Categorias",
                JOptionPane.INFORMATION_MESSAGE, null, categorias, categorias[0]);
    }
}