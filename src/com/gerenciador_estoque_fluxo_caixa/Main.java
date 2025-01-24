package com.gerenciador_estoque_fluxo_caixa;

import com.gerenciador_estoque_fluxo_caixa.controllers.CaixaController;
import com.gerenciador_estoque_fluxo_caixa.controllers.EstoqueController;
import com.gerenciador_estoque_fluxo_caixa.controllers.MenuPrincipalController;
import com.gerenciador_estoque_fluxo_caixa.factory.ControllerFactory;
import com.gerenciador_estoque_fluxo_caixa.hibernateConnection.EntityManagerFactoryService;
import com.gerenciador_estoque_fluxo_caixa.model.domain.NotaFiscal;

public class Main {
    public static void main(String[] args) {
        final EntityManagerFactoryService entityManagerFactoryService = new EntityManagerFactoryService();

        ControllerFactory factory = new ControllerFactory(entityManagerFactoryService);

        EstoqueController estoqueController = factory.createEstoqueController();
        CaixaController caixaController = factory.createCaixaController();

        MenuPrincipalController controller = new MenuPrincipalController(estoqueController, caixaController, entityManagerFactoryService);
        controller.exibirMenuPrincipal();
    }
}

