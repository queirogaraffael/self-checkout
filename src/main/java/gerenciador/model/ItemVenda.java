package gerenciador.model;


import gerenciador.model.pk.ItemVendaPK;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "itemVenda")
public class ItemVenda implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ItemVendaPK id = new ItemVendaPK();

    private Integer quantidade;

    public ItemVenda() {

    }

    public ItemVenda(Venda venda, Produto produto, Integer quantidade) {

        id.setVenda(venda);
        id.setProduto(produto);
        this.quantidade = quantidade;

    }

    public ItemVenda(Produto produto, Integer quantidade) {
        id.setProduto(produto);
        this.quantidade = quantidade;
    }

    public Venda getVenda() {
        return id.getVenda();
    }

    public void setVenda(Venda venda) {
        id.setVenda(venda);
    }

    public Produto getProduto() {
        return id.getProduto();
    }

    public void setProduct(Produto produto) {
        id.setProduto(produto);
    }

    public ItemVendaPK getId() {
        return id;
    }

    public void setId(ItemVendaPK id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double subTotal() {
        return id.getProduto().getPreco() * quantidade;
    }

}
