package com.gerenciador_estoque_fluxo_caixa.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "produtos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigoDeBarra;
    private String nome;
    private Double preco;
    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @OneToMany(mappedBy = "id.produto")
    private Set<ItemVenda> itens = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(codigoDeBarra);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Produto other = (Produto) obj;
        return Objects.equals(codigoDeBarra, other.codigoDeBarra);
    }

    @Override
    public String toString() {
        return "Produto: codigo de barra = " + codigoDeBarra + ", nome = " + nome + ", preco = "
                + String.format("%.2f", preco) + ", quantidade = " + quantidade + ", Categoria = " + categoria;
    }

}
