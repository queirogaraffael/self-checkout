package gerenciador.dto.produto;

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
