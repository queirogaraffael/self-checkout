package gerenciador.factory;


import gerenciador.controller.MenuPrincipalController;
import gerenciador.infrastructure.EntityManagerFactoryService;

public class ApplicationContext {
    private final EntityManagerFactoryService entityManagerFactoryService;
    private final ControllerFactory controllerFactory;

    public ApplicationContext() {
        this.entityManagerFactoryService = new EntityManagerFactoryService();
        this.controllerFactory = new ControllerFactory(new RepositoryFactory(entityManagerFactoryService.entityManagerFactory()));
    }

    public MenuPrincipalController getMenuPrincipalController() {
        return new MenuPrincipalController(entityManagerFactoryService,
                controllerFactory.createEstoqueController(),
                controllerFactory.createCaixaController()
        );
    }
}