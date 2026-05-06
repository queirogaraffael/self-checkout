package gerenciador.dto.itemvenda;


import gerenciador.dto.produto.ProdutoDTO;
import gerenciador.dto.venda.VendaDTO;
import gerenciador.model.ItemVenda;
import java.math.BigDecimal;

public class ItemVendaDTO {

    private ProdutoDTO produto;
    private VendaDTO venda;
    private Integer quantidade;
    private BigDecimal subTotal;

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

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }
}

