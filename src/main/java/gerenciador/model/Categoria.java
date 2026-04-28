package gerenciador.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categorias")
public class Categoria {

	@Id
	private Integer id;
	private String nome;

	@OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
	private Set<Produto> produtos = new HashSet<>();

	public Categoria() {
	}

	public Categoria(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public Categoria(Integer id) {

		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return id + " - " + nome;
	}

}
