package main.java.gerenciador.estoque.caixa.factory;


import main.java.gerenciador.estoque.caixa.controllers.MenuPrincipalController;
import main.java.gerenciador.estoque.caixa.hibernateConnection.EntityManagerFactoryService;

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