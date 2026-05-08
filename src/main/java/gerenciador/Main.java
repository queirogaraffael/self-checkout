package gerenciador;

import gerenciador.controller.MenuPrincipalController;
import gerenciador.config.ApplicationContext;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            ApplicationContext context = new ApplicationContext();
            MenuPrincipalController menuPrincipalController = context.getMenuPrincipalController();
            menuPrincipalController.exibirMenuPrincipal();
        } catch (Exception e) {
            System.err.println("Erro crítico na inicialização: " + e.getMessage());
            JOptionPane.showMessageDialog(null,
                    "Não foi possível conectar ao banco de dados.\nVerifique se o MySQL está ativo e tente novamente.",
                    "Erro de Inicialização", JOptionPane.ERROR_MESSAGE);
        }
    }
}
