package gerenciador.view.admin;

import javax.swing.JOptionPane;

public class PainelAdministrativoView {
	public static String exibirPainelAdministrativo() {

		Object[] opcoesMenu = { "Novo Produto", "Atualizar Produto", "Inventário Completo", "Consultar Produto",
				"Alerta de Reposição", "Gerenciar Categorias", "Excluir Produto",
				"Configurações de Cupom Fiscal", "Histórico de Vendas", "Consultar Recibo de Venda", "Menu Principal" };

		Object opcaoSelecionada = JOptionPane.showInputDialog(null, "Selecione a operação desejada:", "Painel de Administração (Retaguarda)",
				JOptionPane.INFORMATION_MESSAGE, null, opcoesMenu, opcoesMenu[0]);

		if (opcaoSelecionada != null) {
			return opcaoSelecionada.toString();
		}
		return "";

	}

	public static void alertaEntradasInvalida(){
		JOptionPane.showMessageDialog(null,
				"Operação inválida. Por favor, selecione uma das opções disponíveis no menu.");
	}
}
