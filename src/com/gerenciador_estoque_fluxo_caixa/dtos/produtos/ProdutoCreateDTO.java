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

    @Override
    public String toString() {
        StringBuilder mensagem = new StringBuilder();
        mensagem.append("Codigo de Barra: ").append(codigoDeBarra).append("\n")
                .append("Nome: ").append(nome).append("\n")
                .append("Preco: ").append(preco).append("\n")
                .append("Quantidade: ").append(quantidade).append("\n")
                .append("Categoria: ").append(categoria);
        return mensagem.toString();
    }
}
