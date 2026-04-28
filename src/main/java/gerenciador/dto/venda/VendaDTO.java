package gerenciador.dto.venda;


import gerenciador.model.Venda;

import java.time.LocalDateTime;

public class VendaDTO {

    private Integer codigo;
    private LocalDateTime dataHora;
    private Double total;

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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
