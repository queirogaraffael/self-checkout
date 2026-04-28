package gerenciador.dto.venda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendaResponseDTO {

    private Integer codigo;
    private LocalDateTime dataHora;
    private Double total;


    public String getDataHoraString() {
        DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return getDataHora().format(formatoDataHora);
    }


    @Override
    public String toString() {
        return "Venda: Codigo = " + codigo + ", Data = " + getDataHoraString() + ", Total: "
                + String.format("%.2f R$", getTotal());
    }

    public String toStringSemPreco() {
        return "Venda: Codigo = " + codigo + ", Data = " + getDataHoraString();
    }


}
