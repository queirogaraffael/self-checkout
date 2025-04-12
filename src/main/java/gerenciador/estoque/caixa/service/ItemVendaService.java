package main.java.gerenciador.estoque.caixa.service;


import main.java.gerenciador.estoque.caixa.model.dao.ItemVendaDao;
import main.java.gerenciador.estoque.caixa.model.dao.ProdutoDao;
import main.java.gerenciador.estoque.caixa.model.entities.ItemVenda;
import main.java.gerenciador.estoque.caixa.model.entities.Produto;

import java.util.Set;

public class ItemVendaService {

    private final ItemVendaDao itemVendaDao;
    private final ProdutoDao produtoDao;

    public ItemVendaService(ItemVendaDao itemVendaDao, ProdutoDao produtoDao) {
        this.itemVendaDao = itemVendaDao;
        this.produtoDao = produtoDao;
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

    public static ItemVenda retornaItemVendaPeloCodigo(Set<ItemVenda> listaCompras, String codigo) {
        for (ItemVenda p : listaCompras) {
            if (p.getProduto().getCodigoDeBarra().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

    public Set<ItemVenda> retornaItensVenda(Integer codigoVenda) {
        return itemVendaDao.retornaItensVenda(codigoVenda);
    }

    public void adicionaItemVenda(ItemVenda itemVenda) {
        itemVendaDao.adicionaItemVenda(itemVenda);
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

    public ItemVenda criaItemVendaPorCodigoProduto(String codigoBarra, Integer quantidade) {
        Produto produto = produtoDao.retornaProdutoPorCodigo(codigoBarra);
        return new ItemVenda(produto, quantidade);
    }
}
