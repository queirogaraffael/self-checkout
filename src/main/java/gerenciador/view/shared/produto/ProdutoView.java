package gerenciador.view.shared.produto;

import gerenciador.dto.produto.ProdutoCreateDTO;

import javax.swing.*;
import java.math.BigDecimal;

public class ProdutoView {

    // ── Leitura de dados ───────────────────────────────────────────────────────

    public static String leCodigoBarraProduto() {
        return JOptionPane.showInputDialog("Digite o codigo de barra do produto: ");
    }

    public static String leNomeProduto() {
        return JOptionPane.showInputDialog("Digite o nome do produto: ");
    }

    public static BigDecimal leValorProduto() {
        return new BigDecimal(JOptionPane.showInputDialog("Valor do produto: "));
    }

    public static Integer leQuantidadeProduto() {
        return Integer.parseInt(JOptionPane.showInputDialog("Quantidade do produto: "));
    }

    // ── Impressão ──────────────────────────────────────────────────────────────

    public static void printaProdutos(String produtos) {
        JOptionPane.showMessageDialog(null, produtos);
    }

    public static void printaProdutoCriado(ProdutoCreateDTO produtoCreateDTO) {
        JOptionPane.showMessageDialog(null, "Detalhes do Produto:\n\n" + produtoCreateDTO.toString(),
                "Produto Criado", JOptionPane.INFORMATION_MESSAGE);
    }

    // ── Alertas ────────────────────────────────────────────────────────────────

    public static void alertaProdutoJaCadastrado() {
        JOptionPane.showMessageDialog(null, "Produto ja cadastrado anteriormente.");
    }

    public static void alertaProdutoCriadoComSucesso() {
        JOptionPane.showMessageDialog(null, "Produto criado com sucesso!");
    }

    public static void alertaListaProdutoVazia() {
        JOptionPane.showMessageDialog(null, "Lista de produtos vazia.");
    }

    public static void alertaProdutoEstoqueBaixo() {
        JOptionPane.showMessageDialog(null, "Sem produtos com baixo estoque!");
    }

    public static void alertaAtualizacaoProduto(boolean status) {
        if (status) {
            JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Problema ao atualizar produto");
        }
    }

    public static void alertaConflitoAtualizacaoProduto() {
        JOptionPane.showMessageDialog(null,
                "Os dados deste produto foram alterados por outro usuário simultaneamente.\nPor favor, reabra o produto para ver os dados atualizados.",
                "Conflito de Atualização",
                JOptionPane.WARNING_MESSAGE);
    }

    public static void alertaProdutoNaoEncontrado() {
        JOptionPane.showMessageDialog(null, "Produto nao encontrado.");
    }
}
