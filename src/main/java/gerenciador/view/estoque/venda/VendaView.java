package gerenciador.view.estoque.venda;

import javax.swing.*;

public class VendaView {
    private static Object[] opcoesListarVendas = {"Listar todas as vendas", "Listar venda por data especifica", "Voltar"};


    public static int listarVendasOpcoes(){
        return JOptionPane.showOptionDialog(null, "Escolha uma opcao: ", "Listagem de vendas",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoesListarVendas, opcoesListarVendas[0]);
    }

}
