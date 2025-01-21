package com.gerenciador_estoque_fluxo_caixa.service;

import java.util.Set;

import com.gerenciador_estoque_fluxo_caixa.model.entities.ItemVenda;

public class ItemVendaService {

	public static boolean contemProduto(Set<ItemVenda> listaCompras, String codigo) {
		boolean JaExiste = listaCompras.stream().anyMatch(p -> p.getProduto().getcodigoDeBarra().equals(codigo));
		return JaExiste;
	}

	public static Double somaPrecos(Set<ItemVenda> listaCompras) {
		double total = 0;

		for (ItemVenda p : listaCompras) {
			total += p.getProduto().getpreco() * p.getQuantidade();
		}
		return total;
	}

	public static void removeProduto(Set<ItemVenda> listaCompras, String codigo) {

		listaCompras.removeIf(p -> p.getProduto().getcodigoDeBarra().equals(codigo));
	}

	public static ItemVenda retornaItemVendaPeloCodigo(Set<ItemVenda> listaCompras, String codigo) {
		for (ItemVenda p : listaCompras) {
			if (p.getProduto().getcodigoDeBarra().equals(codigo)) {
				return p;
			}
		}
		return null;
	}

	public static String geraRelatorioItemVenda(Set<ItemVenda> itens) {

		StringBuilder sb = new StringBuilder();
		for (ItemVenda p : itens) {
			sb.append("Codigo: ").append(p.getProduto().getcodigoDeBarra()).append(", nome = ")
					.append(p.getProduto().getNome()).append(", Preco: ").append(p.getProduto().getpreco())
					.append(" R$").append(", Quantidade: ").append(p.getQuantidade()).append(", Total: ")
					.append(p.subTotal()).append("\n");

		}

		return sb.toString();
	}

}
