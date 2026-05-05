package gerenciador.view.autoatendimento;

import javax.swing.JOptionPane;
import gerenciador.constant.MenuAutoatendimentoConstant;

public class TerminalAutoatendimentoView {
    public static String exibirMenuPrincipal() {

        Object[] opcoesMenu = { 
                MenuAutoatendimentoConstant.ADICIONAR_PRODUTO, 
                MenuAutoatendimentoConstant.SACOLA_COMPRAS, 
                MenuAutoatendimentoConstant.PRODUTOS_EM_ESTOQUE,
                MenuAutoatendimentoConstant.REMOVER_DA_SACOLA, 
                MenuAutoatendimentoConstant.CORRIGIR_QUANTIDADE, 
                MenuAutoatendimentoConstant.LIMPAR_SACOLA,
                MenuAutoatendimentoConstant.FINALIZAR_COMPRA, 
                MenuAutoatendimentoConstant.MENU_PRINCIPAL 
        };

        Object opcaoSelecionada = JOptionPane.showInputDialog(null, "O que você deseja fazer?", "Caixa de Autoatendimento",
                JOptionPane.INFORMATION_MESSAGE, null, opcoesMenu, opcoesMenu[0]);

        if (opcaoSelecionada != null) {
            return opcaoSelecionada.toString();
        }
        return "";

    }
}