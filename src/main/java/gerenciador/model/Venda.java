package gerenciador.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vendas")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Venda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer codigo;
    private LocalDateTime dataHora;
    private BigDecimal total;

    @OneToMany(mappedBy = "id.venda")
    private Set<ItemVenda> itens = new HashSet<>();


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
