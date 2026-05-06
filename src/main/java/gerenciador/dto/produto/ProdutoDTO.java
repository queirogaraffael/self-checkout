package gerenciador.dto.produto;

import gerenciador.model.Categoria;
import gerenciador.model.Produto;
import java.math.BigDecimal;

public class ProdutoDTO {
    private String codigoDeBarra;
    private String nome;
    private BigDecimal preco;
    private Integer quantidade;
    private Categoria categoria;

    public ProdutoDTO() {
    }

    public ProdutoDTO(String codigoDeBarra, String nome, BigDecimal preco, Integer quantidade, Categoria categoria) {
        this.codigoDeBarra = codigoDeBarra;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.categoria = categoria;
    }

    public ProdutoDTO(Produto produto) {
        this.codigoDeBarra = produto.getCodigoDeBarra();
        this.nome = produto.getNome();
        this.preco = produto.getPreco();
        this.quantidade = produto.getQuantidade();
        this.categoria = produto.getCategoria();
    }

    public String getCodigoDeBarra() { return codigoDeBarra; }
    public void setCodigoDeBarra(String codigoDeBarra) { this.codigoDeBarra = codigoDeBarra; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    @Override
    public String toString() {
        return String.format(
                "===== Detalhes do Produto =====\n" +
                        "Código de Barras: %s\n" +
                        "Nome: %s\n" +
                        "Preço: R$ %.2f\n" +
                        "Quantidade: %d\n" +
                        "Categoria: %s\n" +
                        "===============================",
                codigoDeBarra, nome, preco, quantidade, (categoria != null ? categoria.getNome() : "Sem Categoria")
        );
    }
}
