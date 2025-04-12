package main.java.gerenciador.estoque.caixa.ui.produtos;

import javax.swing.*;

public class AlertasProdutoUI {

    public static void alertaProdutoJaCadastrado(){
        JOptionPane.showMessageDialog(null, "Produto ja cadastrado anteriormente.");
    }

    public static void alertaProdutoCriadoComSucesso(){
        JOptionPane.showMessageDialog(null, "Produto criado com sucesso!");
    }

    public static void alertaListaProdutoVazia(){
        JOptionPane.showMessageDialog(null, "Lista de produtos vazia.");
    }

    public static void alertaProdutoEstoqueBaixo(){
        JOptionPane.showMessageDialog(null, "Sem produtos com baixo estoque!");
    }

    private static void alertaProdutoAtualizadoComSucesso(){
        JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso!");
    }

    private static void alertaErroAtualizarProduto(){
        JOptionPane.showMessageDialog(null, "Problema ao atualizar produto");
    }

    public static void alertaAtualizacaoProduto(boolean status) {
        if (status) {
            alertaProdutoAtualizadoComSucesso();
        } else {
            alertaErroAtualizarProduto();
        }
    }

    public static void alertaProdutoNaoEncontrado() {
        JOptionPane.showMessageDialog(null, "Produto nao encontrado.");
    }
}
