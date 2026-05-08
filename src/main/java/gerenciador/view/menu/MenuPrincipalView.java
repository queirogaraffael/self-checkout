package gerenciador.view.menu;

import javax.swing.*;

public class MenuPrincipalView {

    private static final String MENU_TITLE = "Sistema de Varejo / Autoatendimento";
    private static final String MENU_MESSAGE = "Selecione o módulo que deseja acessar:";

    private MenuPrincipalView() {
    }

    private static final Object[] opcoes = { "Painel Administrativo", "Iniciar Autoatendimento", "Encerrar Sistema" };

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
