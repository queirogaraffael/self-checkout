package gerenciador.view.estoque.venda;

import gerenciador.util.ManipulacaoData;

import javax.swing.*;

public class VendaView {

    // ── Menus e Opções ─────────────────────────────────────────────────────────

    private static Object[] opcoesListarVendas = {"Exibir histórico completo", "Filtrar por data específica", "Voltar"};

    public static int listarVendasOpcoes() {
        return JOptionPane.showOptionDialog(null, "Como você deseja visualizar as vendas?", "Relatório de Vendas",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoesListarVendas, opcoesListarVendas[0]);
    }

    // ── Leitura de dados ───────────────────────────────────────────────────────

    public static String leData() {
        return JOptionPane.showInputDialog("Digite uma data no formato " + ManipulacaoData.formatoData);
    }

    public static int leCodigoVenda() {
        return Integer.parseInt(JOptionPane.showInputDialog("Codigo de venda: "));
    }

    // ── Impressão ──────────────────────────────────────────────────────────────

    public static void printarVenda(String resultado) {
        JOptionPane.showMessageDialog(null, resultado);
    }

    // ── Alertas ────────────────────────────────────────────────────────────────

    public static void semResultadoVendaParaData() {
        JOptionPane.showMessageDialog(null, "Sem resultado de vendas para esta data");
    }

    public static void alertaSemVendaRegistrada() {
        JOptionPane.showMessageDialog(null, "Sem venda registrada.");
    }

    public static void alertaVendaInvalida() {
        JOptionPane.showMessageDialog(null, "Venda invalida. Tente outra!");
    }

    public static void alertaDataPosteriorAtual() {
        JOptionPane.showMessageDialog(null, "Data posterior a data atual. Tente novamente!");
    }

    public static void alertaProblemaFormatoData() {
        JOptionPane.showMessageDialog(null, "Problema no formato da data. Tente novamente!");
    }
}

