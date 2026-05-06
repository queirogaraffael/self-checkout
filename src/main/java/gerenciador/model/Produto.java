package gerenciador.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "produtos")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @EqualsAndHashCode.Include
    private String codigoDeBarra;
    private String nome;
    private BigDecimal preco;
    private Integer quantidade;

    @Version
    private Integer version;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @OneToMany(mappedBy = "id.produto")
    private Set<ItemVenda> itens = new HashSet<>();

    @Override
    public String toString() {
        return "Produto: codigo de barra = " + codigoDeBarra + ", nome = " + nome + ", preco = "
                + String.format("%.2f", preco) + ", quantidade = " + quantidade + ", Categoria = " + categoria;
    }

}
