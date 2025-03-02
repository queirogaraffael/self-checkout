package com.gerenciador_estoque_fluxo_caixa.ui.vendas;

import javax.swing.*;

public class VendaUI {
    private static Object[] opcoesListarVendas = {"Listar todas as vendas", "Listar venda por data especifica", "Voltar"};


    public static int listarVendasOpcoes(){
        return JOptionPane.showOptionDialog(null, "Escolha uma opcao: ", "Listagem de vendas",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoesListarVendas, opcoesListarVendas[0]);
    }

}
