package gerenciador.view.estoque.venda;

import javax.swing.*;

public class VendaView {
    private static Object[] opcoesListarVendas = {"Exibir histórico completo", "Filtrar por data específica", "Voltar"};

    public static int listarVendasOpcoes(){
        return JOptionPane.showOptionDialog(null, "Como você deseja visualizar as vendas?", "Relatório de Vendas",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoesListarVendas, opcoesListarVendas[0]);
    }

}
