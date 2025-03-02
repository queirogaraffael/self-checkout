package com.gerenciador_estoque_fluxo_caixa.ui;

import com.gerenciador_estoque_fluxo_caixa.model.domain.NotaFiscal;

import javax.swing.*;

public class NotaFiscalUI {

    private static Object[] opcoes = {"Sim", "Nao"};

    public static int opcaoNotaFiscal(String mensagem) {
        return JOptionPane.showOptionDialog(null, mensagem, "Opcoes", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
    }

    public static String verificarAcao(NotaFiscal notaFiscal) {
        return notaFiscal.getStatusNotaFiscal()
                ? "Deseja modificar o diretorio?"
                : "Ativar gerador de nota fiscal?";
    }

    public static String mensagemSucesso(NotaFiscal notaFiscal) {
        return notaFiscal.getStatusNotaFiscal()
                ? "Gerador de notas fiscais com novo diretorio ativado com sucesso!"
                : "Gerador de notas fiscais ativado com sucesso!";
    }

    public static String mensagemFalha(NotaFiscal notaFiscal) {
        return notaFiscal.getStatusNotaFiscal()
                ? "Falha ao tentar ativar o novo diretorio do gerador de notas fiscais."
                : "Falha ao tentar ativar gerador de notas fiscais.";
    }

    public static String leCaminhoNotaFiscal(){
        return JOptionPane.showInputDialog("Caminho do diretorio: ");
    }

    public static void printaMensagem(String mensagem){
        JOptionPane.showMessageDialog(null, mensagem);
    }

}
