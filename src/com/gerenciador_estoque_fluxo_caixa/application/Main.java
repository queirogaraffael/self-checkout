package com.gerenciador_estoque_fluxo_caixa.application;

import com.gerenciador_estoque_fluxo_caixa.controllers.CaixaController;
import com.gerenciador_estoque_fluxo_caixa.controllers.EstoqueController;
import com.gerenciador_estoque_fluxo_caixa.controllers.MenuPrincipalController;
import com.gerenciador_estoque_fluxo_caixa.factory.AppInitializer;
import com.gerenciador_estoque_fluxo_caixa.factory.ControllerFactory;
import com.gerenciador_estoque_fluxo_caixa.hibernateConnection.EntityManagerFactoryService;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactoryService entityManagerFactoryService = new EntityManagerFactoryService();

        entityManagerFactoryService.inicializarEntityManagerFactory();

        ControllerFactory factory = AppInitializer.initControllerFactory(entityManagerFactoryService);

        EstoqueController estoqueController = factory.createEstoqueController();
        CaixaController caixaController = factory.createCaixaController();

        MenuPrincipalController controller = new MenuPrincipalController(
                estoqueController,
                caixaController,
                entityManagerFactoryService
        );

        controller.exibirMenuPrincipal();
    }
}


