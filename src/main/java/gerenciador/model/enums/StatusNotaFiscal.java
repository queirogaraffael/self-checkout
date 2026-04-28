package gerenciador.model.enums;

public enum StatusNotaFiscal {

    DESATIVADA(0, "Desativada"),
    ATIVADA(1, "Ativada");

    private final int codigo;
    private final String descricao;

    StatusNotaFiscal(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusNotaFiscal porCodigo(int codigo) {
        for (StatusNotaFiscal status : StatusNotaFiscal.values()) {
            if (status.getCodigo() == codigo) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código de status inválido: " + codigo);
    }

    @Override
    public String toString() {
        return descricao;
    }
}
