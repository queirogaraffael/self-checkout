package com.gerenciador_estoque_fluxo_caixa.views;

import javax.swing.JOptionPane;

public class FluxoDeCaixaView {
	public static String exibirMenuFluxoDeCaixa() {

		Object[] opcoesMenu = { "Adicionar Produto", "Sacola de Compras", "Produtos em Estoque",
				"Remover da Sacola", "Alterar Quantidade", "Limpar Sacola",
				"Finalizar Compra", "Menu Principal" };

		Object opcaoSelecionada = JOptionPane.showInputDialog(null, "Escolha uma opcao", "Fluxo De Caixa",
				JOptionPane.INFORMATION_MESSAGE, null, opcoesMenu, opcoesMenu[0]);

		if (opcaoSelecionada != null) {
			return opcaoSelecionada.toString();
		}
		return "";

	}
}
