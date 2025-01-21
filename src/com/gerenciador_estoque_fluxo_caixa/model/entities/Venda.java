package com.gerenciador_estoque_fluxo_caixa.model.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "vendas")
public class Venda implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codigo;
	private LocalDateTime dataHora;
	private Double total;

	@OneToMany(mappedBy = "id.venda")
	private Set<ItemVenda> itens = new HashSet<>();

	public Venda() {
	}

	public Integer getCodigo() {
		return codigo;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public String getDataHoraString() {
		DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		return getDataHora().format(formatoDataHora);
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public Set<ItemVenda> getProdutosVendidos() {
		return itens;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Venda: Codigo = " + codigo + ", Data = " + getDataHoraString() + ", Total: "
				+ String.format("%.2f R$", getTotal());
	}

	public String toStringSemPreco() {
		return "Venda: Codigo = " + codigo + ", Data = " + getDataHoraString();
	}

}
