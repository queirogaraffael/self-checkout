package com.gerenciador_estoque_fluxo_caixa.model.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "produtos")
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String codigoDeBarra;
	private String nome;
	private Double preco;
	private Integer quantidade;
	private Integer categoria;

	@OneToMany(mappedBy = "id.produto")
	private Set<ItemVenda> itens = new HashSet<>();

	public Produto() {

	}

	public Produto(String codigoDeBarra, String nome, Double preco, Integer quantidade, Integer categoria) {
		this.codigoDeBarra = codigoDeBarra;
		this.nome = nome;
		this.preco = preco;
		this.quantidade = quantidade;
		this.categoria = categoria;
	}

	public String getcodigoDeBarra() {
		return codigoDeBarra;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getpreco() {
		return preco;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public void setpreco(Double preco) {
		this.preco = preco;
	}

	public Integer getCategoria() {
		return categoria;
	}

	public void setCategoria(Integer categoria) {
		this.categoria = categoria;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigoDeBarra);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		return Objects.equals(codigoDeBarra, other.codigoDeBarra);
	}

	@Override
	public String toString() {
		return "Produto: codigo de barra = " + codigoDeBarra + ", nome = " + nome + ", preco = "
				+ String.format("%.2f", preco) + ", quantidade = " + quantidade + ", Categoria = " + categoria;
	}

}
