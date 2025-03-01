package com.gerenciador_estoque_fluxo_caixa.dtos.produtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoBaixoEstoqueResponseDTO {

    private String codigoDeBarra;
    private String nome;
    private Integer quantidade;

    @Override
    public String toString() {
        return "Produto: codigo de barra = " + codigoDeBarra + ", nome = " + nome + ", quantidade = " + quantidade;
    }
}
