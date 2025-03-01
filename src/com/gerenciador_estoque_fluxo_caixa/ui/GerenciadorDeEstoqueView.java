package com.gerenciador_estoque_fluxo_caixa.ui;

import javax.swing.JOptionPane;

public class GerenciadorDeEstoqueView {
	public static String exibirMenuGerenciadorDeEstoque() {

		Object[] opcoesMenu = { "Cadastrar Produto", "Editar Produto", "Estoque",
				"Estoque Baixo", "Categorias", "Remover Produto",
				"Configurar Nota Fiscal", "Vendas", "Detalhes Venda", "Menu Principal" };

		Object opcaoSelecionada = JOptionPane.showInputDialog(null, "Escolha uma opcao", "Gerenciador de Estoque",
				JOptionPane.INFORMATION_MESSAGE, null, opcoesMenu, opcoesMenu[0]);

		if (opcaoSelecionada != null) {
			return opcaoSelecionada.toString();
		}
		return "";

	}

	public static void alertaEntradasInvalida(){
		JOptionPane.showMessageDialog(null,
				"Entrada invalida. Por favor, insira um numero correspondente a opcao desejada.");
	}
}
