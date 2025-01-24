package com.gerenciador_estoque_fluxo_caixa.dtos.produtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProdutoDTO {
    private String codigoDeBarra;
    private String nome;
    private Double preco;
}
