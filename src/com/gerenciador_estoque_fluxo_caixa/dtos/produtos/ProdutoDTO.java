package com.gerenciador_estoque_fluxo_caixa.dtos.produtos;

import com.gerenciador_estoque_fluxo_caixa.model.entities.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;

public class ProdutoDTO {

    private Long id;
    private String nome;
    private Double preco;
    private String codigoDeBarra;

    public ProdutoDTO() {
    }

    public ProdutoDTO(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.preco = produto.getPreco();
        this.codigoDeBarra = produto.getCodigoDeBarra();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Double getPreco() {
        return preco;
    }

    public String getCodigoDeBarra() {
        return codigoDeBarra;
    }
}

