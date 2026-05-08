package gerenciador.view.autoatendimento;

import gerenciador.constant.MenuAutoatendimentoConstant;

import javax.swing.*;
import java.math.BigDecimal;

public class AutoatendimentoView {

    // ── Menu Principal ─────────────────────────────────────────────────────────

    public static String exibirMenuPrincipal() {
        Object[] opcoesMenu = {
                MenuAutoatendimentoConstant.ADICIONAR_PRODUTO,
                MenuAutoatendimentoConstant.SACOLA_COMPRAS,
                MenuAutoatendimentoConstant.REMOVER_DA_SACOLA,
                MenuAutoatendimentoConstant.CORRIGIR_QUANTIDADE,
                MenuAutoatendimentoConstant.LIMPAR_SACOLA,
                MenuAutoatendimentoConstant.FINALIZAR_COMPRA,
                MenuAutoatendimentoConstant.MENU_PRINCIPAL
        };

        Object opcaoSelecionada = JOptionPane.showInputDialog(null, "O que você deseja fazer?",
                "Caixa de Autoatendimento", JOptionPane.INFORMATION_MESSAGE, null, opcoesMenu, opcoesMenu[0]);

        if (opcaoSelecionada != null) {
            return opcaoSelecionada.toString();
        }
        return "";
    }

    public static void alertaEntradaInvalida() {
        JOptionPane.showMessageDialog(null,
                "Entrada invalida. Por favor, insira um numero correspondente a opcao desejada.");
    }

    // ── Adicionar Produto ──────────────────────────────────────────────────────

    public static String leCodigoProduto() {
        return JOptionPane.showInputDialog("Digite o codigo do produto que voce deseja adicionar a lista de compras");
    }

    public static Integer leNovamenteQuantidade() {
        return Integer.parseInt(JOptionPane.showInputDialog(
                "Produto ja adicionado anteriormente, digite a nova quantidade que voce deseja: "));
    }

    public static int exibirDialogoConfirmacaoAdicionarItensRestantes() {
        String[] opcoes = {"Sim", "Não"};
        return JOptionPane.showOptionDialog(
                null,
                "Deseja adicionar todos os itens restantes ?",
                "Quantidade desejada menor do que em estoque.",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );
    }

    public static void alertaProdutoIndisponivel() {
        JOptionPane.showMessageDialog(null, "Produto indisponivel. Tente outro!");
    }

    public static void alertaCompraProdutoCancelada() {
        JOptionPane.showMessageDialog(null, "Compra de produto cancelada.");
    }

    public static void alertaProdutoSemEstoque() {
        JOptionPane.showMessageDialog(null, "Produto sem estoque. Tente outro!");
    }

    // ── Sacola de Compras ──────────────────────────────────────────────────────

    public static void exibirSacola(BigDecimal subtotal, String compras) {
        String subtotalFormatado = String.format("Subtotal: R$ %.2f", subtotal);
        String mensagem = "Produtos da sacola de compras:\n" + compras + "\n" + subtotalFormatado;
        JOptionPane.showMessageDialog(null, mensagem, "Sacola de Compras", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void alertaSacolaLimpaSucesso() {
        JOptionPane.showMessageDialog(null, "Sacola de compras limpada com sucesso.");
    }

    // ── Remover Produto ────────────────────────────────────────────────────────

    public static void alertaSacolaVazia() {
        JOptionPane.showMessageDialog(null, "Adicione primeiro um produto a sua sacola para poder remover.");
    }

    public static void alertaProdutoRemovidoComSucesso() {
        JOptionPane.showMessageDialog(null, "Produto removida com sucesso!");
    }

    public static void alertaProdutoJaNaoConstava() {
        JOptionPane.showMessageDialog(null,
                "Produto ja nao constava na sacola. Tente novamente com uma produto existente.");
    }

    // ── Corrigir Quantidade ────────────────────────────────────────────────────

    public static void alertaCarrinhoVazio() {
        JOptionPane.showMessageDialog(null, "Carrinho de compras vazio.");
    }

    public static void alertaQuantidadeProdutoCorrigida() {
        JOptionPane.showMessageDialog(null, "Quantidade corrigida com sucesso!");
    }

    public static void alertaProdutoInvalido() {
        JOptionPane.showMessageDialog(null, "Produto invalido, tente outro!");
    }
}
