package gerenciador.infrastructure.exception;

public class ProdutoEsgotadoException extends RuntimeException {

    private final String nomeProduto;

    public ProdutoEsgotadoException(String nomeProduto) {
        super("Produto esgotado: " + nomeProduto);
        this.nomeProduto = nomeProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }
}
