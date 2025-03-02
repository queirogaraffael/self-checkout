package com.gerenciador_estoque_fluxo_caixa.dtos.produtos;

public class ProdutoAtualizarQuantidadeDTO {
    private Integer quantidade;

    public ProdutoAtualizarQuantidadeDTO() {
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
