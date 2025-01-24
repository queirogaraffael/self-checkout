package com.gerenciador_estoque_fluxo_caixa.service;

import com.gerenciador_estoque_fluxo_caixa.hibernateConnection.EntityManagerFactoryService;
import com.gerenciador_estoque_fluxo_caixa.model.dao.ItemVendaDao;
import com.gerenciador_estoque_fluxo_caixa.model.dao.imp.ItemVendaDaoHibernate;
import com.gerenciador_estoque_fluxo_caixa.model.entities.ItemVenda;

import javax.persistence.EntityManagerFactory;
import java.util.Set;

public class ItemVendaService {

    private ItemVendaDao itemVendaDao;

    public ItemVendaService(EntityManagerFactory entityManagerFactory) {
        this.itemVendaDao = new ItemVendaDaoHibernate(entityManagerFactory);
    }

    public boolean contemProduto(Set<ItemVenda> listaCompras, String codigo) {
        return listaCompras.stream().anyMatch(p -> p.getProduto().getCodigoDeBarra().equals(codigo));
    }

    public Double somaPrecos(Set<ItemVenda> listaCompras) {
        double total = 0;

        for (ItemVenda p : listaCompras) {
            total += p.getProduto().getPreco() * p.getQuantidade();
        }
        return total;
    }

    public void removeProduto(Set<ItemVenda> listaCompras, String codigo) {
        listaCompras.removeIf(p -> p.getProduto().getCodigoDeBarra().equals(codigo));
    }

    public ItemVenda retornaItemVendaPeloCodigo(Set<ItemVenda> listaCompras, String codigo) {
        for (ItemVenda p : listaCompras) {
            if (p.getProduto().getCodigoDeBarra().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

    public String geraRelatorioItemVenda(Set<ItemVenda> itens) {

        StringBuilder sb = new StringBuilder();
        for (ItemVenda p : itens) {
            sb.append("Codigo: ").append(p.getProduto().getCodigoDeBarra()).append(", nome = ")
                    .append(p.getProduto().getNome()).append(", Preco: ").append(p.getProduto().getPreco())
                    .append(" R$").append(", Quantidade: ").append(p.getQuantidade()).append(", Total: ")
                    .append(p.subTotal()).append("\n");

        }

        return sb.toString();
    }

}
