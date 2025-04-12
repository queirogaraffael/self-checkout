package main.java.gerenciador.estoque.caixa.dtos.itemvenda;


import main.java.gerenciador.estoque.caixa.dtos.produtos.ProdutoDTO;
import main.java.gerenciador.estoque.caixa.dtos.vendas.VendaDTO;
import main.java.gerenciador.estoque.caixa.model.entities.ItemVenda;

public class ItemVendaDTO {

    private ProdutoDTO produto;
    private VendaDTO venda;
    private Integer quantidade;
    private Double subTotal;

    public ItemVendaDTO() {
    }

    public ItemVendaDTO(ItemVenda item) {
        this.produto = new ProdutoDTO(item.getProduto());
        this.venda = item.getVenda() != null ? new VendaDTO(item.getVenda()) : null;
        this.quantidade = item.getQuantidade();
        this.subTotal = item.subTotal();
    }

    public ProdutoDTO getProduto() {
        return produto;
    }

    public VendaDTO getVenda() {
        return venda;
    }

    public void setVenda(VendaDTO venda) {
        this.venda = venda;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }
}

