package main.java.gerenciador.estoque.caixa.dtos.produtos;

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
        return "Produto = Codigo de barra: " + codigoDeBarra + ", Nome: " + nome + ", Quantidade: " + quantidade;
    }
}
