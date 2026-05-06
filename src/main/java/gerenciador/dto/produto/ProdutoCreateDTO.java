package gerenciador.dto.produto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import gerenciador.model.Categoria;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoCreateDTO {

    private String codigoDeBarra;
    private String nome;
    private BigDecimal preco;
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
