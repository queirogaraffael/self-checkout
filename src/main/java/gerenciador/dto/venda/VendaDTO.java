package gerenciador.dto.venda;


import gerenciador.model.Venda;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class VendaDTO {

    private Integer codigo;
    private LocalDateTime dataHora;
    private BigDecimal total;

    public VendaDTO() {
    }

    public VendaDTO(Venda venda) {
        this.codigo = venda.getCodigo();
        this.dataHora = venda.getDataHora();
        this.total = venda.getTotal();
    }

    public Integer getCodigo() {
        return codigo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
