package com.gerenciador_estoque_fluxo_caixa;

import com.gerenciador_estoque_fluxo_caixa.controllers.MenuPrincipalController;
import com.gerenciador_estoque_fluxo_caixa.hibernateConnection.EntityManagerFactoryService;
import com.gerenciador_estoque_fluxo_caixa.model.domain.NotaFiscal;

public class Main {
	public static void main(String[] args) {

		MenuPrincipalController controller = new MenuPrincipalController(new EntityManagerFactoryService(), new NotaFiscal());
		controller.exibirMenuPrincipal();
	}
}
