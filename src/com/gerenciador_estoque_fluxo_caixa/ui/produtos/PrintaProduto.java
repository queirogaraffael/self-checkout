package com.gerenciador_estoque_fluxo_caixa.ui.produtos;

import com.gerenciador_estoque_fluxo_caixa.dtos.produtos.ProdutoCreateDTO;

import javax.swing.*;

public class PrintaProduto {

    public static void exibeProdutoCriado(ProdutoCreateDTO produtoCreateDTO) {
        StringBuilder mensagem = new StringBuilder();

        mensagem.append("Codigo de Barra: ").append(produtoCreateDTO.getCodigoDeBarra()).append("\n")
                .append("Nome: ").append(produtoCreateDTO.getNome()).append("\n")
                .append("Preco: ").append(produtoCreateDTO.getPreco()).append("\n")
                .append("Quantidade: ").append(produtoCreateDTO.getQuantidade()).append("\n")
                .append("\nCategoria: ").append(produtoCreateDTO.getCategoria().getNome());

        JOptionPane.showMessageDialog(null, mensagem.toString(), "Detalhes do Produto Criado", JOptionPane.INFORMATION_MESSAGE);
    }
}
