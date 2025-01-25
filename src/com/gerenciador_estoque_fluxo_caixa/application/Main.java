package com.gerenciador_estoque_fluxo_caixa.application;

import com.gerenciador_estoque_fluxo_caixa.controllers.MenuPrincipalController;
import com.gerenciador_estoque_fluxo_caixa.factory.ApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ApplicationContext();
        MenuPrincipalController menuPrincipalController = context.getMenuPrincipalController();
        menuPrincipalController.exibirMenuPrincipal();
    }
}
