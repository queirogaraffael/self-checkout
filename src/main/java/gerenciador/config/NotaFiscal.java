package gerenciador.config;

import gerenciador.model.enums.StatusNotaFiscal;

public class NotaFiscal {

    private StatusNotaFiscal statusNotaFiscal;
    private String caminhoNotaFiscal;

    public NotaFiscal() {
        this.statusNotaFiscal = StatusNotaFiscal.DESATIVADA;
        this.caminhoNotaFiscal = "";
    }

    public StatusNotaFiscal getStatusNotaFiscal() {
        return statusNotaFiscal;
    }

    public void setStatusNotaFiscal(StatusNotaFiscal statusNotaFiscal) {
        this.statusNotaFiscal = statusNotaFiscal;
    }

    public String getCaminhoNotaFiscal() {
        return caminhoNotaFiscal;
    }

    public void setCaminhoNotaFiscal(String caminhoNotaFiscal) {
        this.caminhoNotaFiscal = caminhoNotaFiscal;
    }
}

