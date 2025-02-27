package com.gerenciador_estoque_fluxo_caixa.dtos.produtos;

import com.gerenciador_estoque_fluxo_caixa.model.entities.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoCreateDTO {

    private String codigoDeBarra;
    private String nome;
    private Double preco;
    private Integer quantidade;
    private Categoria categoria;

}
