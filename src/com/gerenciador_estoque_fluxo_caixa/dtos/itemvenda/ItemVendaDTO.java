package com.gerenciador_estoque_fluxo_caixa.dtos.itemvenda;

import com.gerenciador_estoque_fluxo_caixa.dtos.produtos.ProdutoDTO;
import com.gerenciador_estoque_fluxo_caixa.dtos.vendas.VendaDTO;
import com.gerenciador_estoque_fluxo_caixa.model.entities.ItemVenda;

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

    public Integer getQuantidade() {
        return quantidade;
    }

    public Double getSubTotal() {
        return subTotal;
    }
}

