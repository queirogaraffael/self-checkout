package main.java.gerenciador.estoque.caixa.ui;

import javax.swing.*;

public class MenuPrincipalControllerView {

    private static final String MENU_TITLE = "Sistema Estoque-Caixa";
    private static final String MENU_MESSAGE = "Escolha uma opcao: ";

    private MenuPrincipalControllerView() {
    }

    private static final Object[] opcoes = { "Gerenciador de Estoque", "Fluxo de Caixa", "Encerrar programa" };

    public static int exibeViewMenuPrincipal() {
        return JOptionPane.showOptionDialog(
                null,
                MENU_MESSAGE,
                MENU_TITLE,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );

    }


}
