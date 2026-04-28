package gerenciador.view.shared.produto;


import gerenciador.dto.produto.ProdutoCreateDTO;

import javax.swing.*;

public class PrintaProdutoView {

    public static void printaProdutos(String produtos){
        JOptionPane.showMessageDialog(null, produtos);
    }

    public static void printaProdutoCriado(ProdutoCreateDTO produtoCreateDTO){
        JOptionPane.showMessageDialog(null, "Detalhes do Produto:\n\n" + produtoCreateDTO.toString(), "Produto Criado", JOptionPane.INFORMATION_MESSAGE);
    }
}
