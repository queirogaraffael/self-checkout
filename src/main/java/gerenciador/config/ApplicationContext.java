package gerenciador.config;


import gerenciador.controller.MenuPrincipalController;
import gerenciador.infrastructure.JPAManager;
import gerenciador.infrastructure.RepositoryFactory;

public class ApplicationContext {
    private final JPAManager entityManagerFactoryService;
    private final ControllerRegistry controllerFactory;

    public ApplicationContext() {
        this.entityManagerFactoryService = new JPAManager();
        this.controllerFactory = new ControllerRegistry(new RepositoryFactory(entityManagerFactoryService.entityManagerFactory()));
    }

    public MenuPrincipalController getMenuPrincipalController() {
        return new MenuPrincipalController(entityManagerFactoryService,
                controllerFactory.createPainelAdministrativoController(),
                controllerFactory.createAutoatendimentoController()
        );
    }
}