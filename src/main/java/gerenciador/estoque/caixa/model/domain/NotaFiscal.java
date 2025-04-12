package main.java.gerenciador.estoque.caixa.model.domain;

public class NotaFiscal {

	private boolean statusNotaFiscal;
	private String caminhoNotaFiscal;

	public NotaFiscal() {
		this.statusNotaFiscal = false;
		this.caminhoNotaFiscal = "";
	}

	public boolean getStatusNotaFiscal() {
		return statusNotaFiscal;
	}

	public void setStatusNotaFiscal(Boolean statusNotaFiscal) {
		this.statusNotaFiscal = statusNotaFiscal;
	}

	public String getCaminhoNotaFiscal() {
		return caminhoNotaFiscal;
	}

	public void setCaminhoNotaFiscal(String caminhoNotaFiscal) {
		this.caminhoNotaFiscal = caminhoNotaFiscal;
	}

}
