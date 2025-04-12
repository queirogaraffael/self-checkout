package main.java.gerenciador.estoque.caixa;


import main.java.gerenciador.estoque.caixa.controllers.MenuPrincipalController;
import main.java.gerenciador.estoque.caixa.factory.ApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ApplicationContext();
        MenuPrincipalController menuPrincipalController = context.getMenuPrincipalController();
        menuPrincipalController.exibirMenuPrincipal();
    }
}
