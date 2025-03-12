package com.gerenciador_estoque_fluxo_caixa.ui.produtos;

import com.gerenciador_estoque_fluxo_caixa.dtos.produtos.ProdutoCreateDTO;

import javax.swing.*;

public class PrintaProdutoUI {

    public static void printaProdutos(String produtos){
        JOptionPane.showMessageDialog(null, produtos);
    }

    public static void printaProdutoCriado(ProdutoCreateDTO produtoCreateDTO){
        JOptionPane.showMessageDialog(null, "Detalhes do Produto:\n\n" + produtoCreateDTO.toString(), "Produto Criado", JOptionPane.INFORMATION_MESSAGE);
    }
}
