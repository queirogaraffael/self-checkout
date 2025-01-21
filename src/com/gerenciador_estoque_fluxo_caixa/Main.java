package com.gerenciador_estoque_fluxo_caixa;

import com.gerenciador_estoque_fluxo_caixa.controllers.MenuPrincipalController;
import com.gerenciador_estoque_fluxo_caixa.hibernateConnection.EntityManagerFactoryService;

public class Main {
	public static void main(String[] args) {
		EntityManagerFactoryService.inicializarEntityManagerFactory();

		MenuPrincipalController controller = new MenuPrincipalController();
		controller.exibirMenuPrincipal();
	}
}
