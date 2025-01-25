package com.gerenciador_estoque_fluxo_caixa.factory;

import com.gerenciador_estoque_fluxo_caixa.controllers.MenuPrincipalController;
import com.gerenciador_estoque_fluxo_caixa.hibernateConnection.EntityManagerFactoryService;

public class ApplicationContext {
    private final EntityManagerFactoryService entityManagerFactoryService;
    private final ControllerFactory controllerFactory;

    public ApplicationContext() {
        this.entityManagerFactoryService = new EntityManagerFactoryService();
        this.controllerFactory = new ControllerFactory(new DaoFactory(entityManagerFactoryService.entityManagerFactory()));
    }

    public MenuPrincipalController getMenuPrincipalController() {
        return new MenuPrincipalController(entityManagerFactoryService,
                controllerFactory.createEstoqueController(),
                controllerFactory.createCaixaController()
        );
    }
}