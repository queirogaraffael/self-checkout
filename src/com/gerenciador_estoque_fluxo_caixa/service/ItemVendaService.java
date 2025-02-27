package com.gerenciador_estoque_fluxo_caixa.service;

import com.gerenciador_estoque_fluxo_caixa.hibernateConnection.EntityManagerFactoryService;
import com.gerenciador_estoque_fluxo_caixa.model.dao.ItemVendaDao;
import com.gerenciador_estoque_fluxo_caixa.model.dao.imp.ItemVendaDaoHibernate;
import com.gerenciador_estoque_fluxo_caixa.model.entities.ItemVenda;
import com.gerenciador_estoque_fluxo_caixa.model.entities.Venda;

import javax.persistence.EntityManagerFactory;
import java.util.Set;

public class ItemVendaService {

    private final ItemVendaDao itemVendaDao;

    public ItemVendaService(ItemVendaDao itemVendaDao) {
        this.itemVendaDao = itemVendaDao;
    }

    public static boolean contemProduto(Set<ItemVenda> listaCompras, String codigo) {
        return listaCompras.stream().anyMatch(p -> p.getProduto().getCodigoDeBarra().equals(codigo));
    }

    public static Double somaPrecos(Set<ItemVenda> listaCompras) {
        double total = 0;

        for (ItemVenda p : listaCompras) {
            total += p.getProduto().getPreco() * p.getQuantidade();
        }
        return total;
    }

    public void removeProduto(Set<ItemVenda> listaCompras, String codigo) {
        listaCompras.removeIf(p -> p.getProduto().getCodigoDeBarra().equals(codigo));
    }

    public static ItemVenda retornaItemVendaPeloCodigo(Set<ItemVenda> listaCompras, String codigo) {
        for (ItemVenda p : listaCompras) {
            if (p.getProduto().getCodigoDeBarra().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

    public static String geraRelatorioItemVenda(Set<ItemVenda> itens) {

        StringBuilder sb = new StringBuilder();
        for (ItemVenda p : itens) {
            sb.append("Codigo: ").append(p.getProduto().getCodigoDeBarra()).append(", nome = ")
                    .append(p.getProduto().getNome()).append(", Preco: ").append(p.getProduto().getPreco())
                    .append(" R$").append(", Quantidade: ").append(p.getQuantidade()).append(", Total: ")
                    .append(p.subTotal()).append("\n");

        }

        return sb.toString();
    }

    public Set<ItemVenda> retornaItensVenda(Venda venda) {
        return null;
    }

    public void adicionaItemVenda(ItemVenda itemVenda) {

    }
}
