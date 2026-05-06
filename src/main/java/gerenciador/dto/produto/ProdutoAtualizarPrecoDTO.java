package gerenciador.dto.produto;

import java.math.BigDecimal;

public class ProdutoAtualizarPrecoDTO {

    private BigDecimal preco;

    public ProdutoAtualizarPrecoDTO() {
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }


}
