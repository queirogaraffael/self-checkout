package gerenciador.view.menu;

import javax.swing.*;

public class MenuPrincipalView {

    private static final String MENU_TITLE = "Menu Administrativo";
    private static final String MENU_MESSAGE = "Selecione uma opção:";

    private MenuPrincipalView() {
    }

    private static final Object[] opcoes = { "Gerenciador de Estoque", "Iniciar Autoatendimento", "Encerrar programa" };

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
