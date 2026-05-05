package gerenciador;


import gerenciador.controller.MenuPrincipalController;
import gerenciador.config.ApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ApplicationContext();
        MenuPrincipalController menuPrincipalController = context.getMenuPrincipalController();
        menuPrincipalController.exibirMenuPrincipal();
    }
}
