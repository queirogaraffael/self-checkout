package gerenciador.model.enums;

public enum ResultadoFinalizacao {

    SUCESSO,
    PRODUTO_ESGOTADO,
    SISTEMA_OCUPADO;

    private String nomeProduto;

    public ResultadoFinalizacao comNome(String nome) {
        this.nomeProduto = nome;
        return this;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }
}
