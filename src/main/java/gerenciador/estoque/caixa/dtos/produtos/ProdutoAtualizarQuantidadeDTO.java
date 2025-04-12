package main.java.gerenciador.estoque.caixa.dtos.produtos;

public class ProdutoAtualizarQuantidadeDTO {
    private Integer quantidade;

    public ProdutoAtualizarQuantidadeDTO() {
    }

    public ProdutoAtualizarQuantidadeDTO(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
