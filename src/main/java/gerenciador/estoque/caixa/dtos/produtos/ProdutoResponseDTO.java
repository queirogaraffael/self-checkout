package main.java.gerenciador.estoque.caixa.dtos.produtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoResponseDTO {
    private String codigoDeBarra;
    private String nome;

    @Override
    public String toString() {
        return "Produto: codigo de barra = " + codigoDeBarra + ", nome = " + nome;
    }
}
