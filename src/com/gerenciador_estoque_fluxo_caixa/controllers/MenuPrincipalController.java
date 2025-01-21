package com.gerenciador_estoque_fluxo_caixa.controllers;

import javax.swing.JOptionPane;

import com.gerenciador_estoque_fluxo_caixa.constantes.ConstantesMenuPrincipal;
import com.gerenciador_estoque_fluxo_caixa.utils.AutenticadorDeSenha;
import com.gerenciador_estoque_fluxo_caixa.hibernateConnection.EntityManagerFactoryService;
import com.gerenciador_estoque_fluxo_caixa.model.dao.CategoriaDao;
import com.gerenciador_estoque_fluxo_caixa.model.dao.DaoFactory;
import com.gerenciador_estoque_fluxo_caixa.model.dao.ItemVendaDao;
import com.gerenciador_estoque_fluxo_caixa.model.dao.ProdutoDao;
import com.gerenciador_estoque_fluxo_caixa.model.dao.VendaDao;
import com.gerenciador_estoque_fluxo_caixa.model.domain.NotaFiscal;

public class MenuPrincipalController {
	private EstoqueController estoqueController;
	private CaixaController caixaController;
	private NotaFiscal notaFiscal;
	private CategoriaDao categoriaDao;
	private ItemVendaDao itemVendaDao;
	private ProdutoDao produtoDao;
	private VendaDao vendaDao;

	public MenuPrincipalController() {
		this.notaFiscal = new NotaFiscal();

		this.categoriaDao = DaoFactory.createCategoriaDao();
		this.itemVendaDao = DaoFactory.createItemVendaDao();
		this.produtoDao = DaoFactory.createProdutoDao();
		this.vendaDao = DaoFactory.createVendaDao();

		this.estoqueController = new EstoqueController(notaFiscal, categoriaDao, itemVendaDao, produtoDao, vendaDao);
		this.caixaController = new CaixaController(notaFiscal, categoriaDao, itemVendaDao, produtoDao, vendaDao);

	}

	public void exibirMenuPrincipal() {
		int opcaoMenuPrincipal = 0;
		Object[] opcoes = { "Gerenciador de Estoque", "Fluxo de Caixa", "Encerrar programa" };

		do {

			opcaoMenuPrincipal = JOptionPane.showOptionDialog(null, "Escolha uma opcao: ", "Menu Principal",
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);

			switch (opcaoMenuPrincipal) {
			case (ConstantesMenuPrincipal.GERENCIADOR_ESTOQUE):
				String senhaDigitada = JOptionPane.showInputDialog(null, "Digite a senha: ");
				boolean autenticacao = AutenticadorDeSenha.autenticacaoSenha(senhaDigitada);

				if (autenticacao) {

					estoqueController.gerenciadorEstoque();
				} else {
					JOptionPane.showMessageDialog(null, "Senha incorreta. Tente novamente!");
				}

				break;
			case (ConstantesMenuPrincipal.FLUXO_CAIXA):
				caixaController.fluxoDeCaixa();
				break;
			default:

				EntityManagerFactoryService.fechaEntityManagerFactory();
				System.exit(0);
			}
		} while (opcaoMenuPrincipal != ConstantesMenuPrincipal.SAIR);
	}
}
