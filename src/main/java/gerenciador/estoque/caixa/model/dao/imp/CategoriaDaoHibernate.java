package main.java.gerenciador.estoque.caixa.model.dao.imp;

import main.java.gerenciador.estoque.caixa.dtos.categorias.CategoriaResponseDTO;
import main.java.gerenciador.estoque.caixa.model.dao.CategoriaDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collections;
import java.util.List;

public class CategoriaDaoHibernate implements CategoriaDao {

	private final EntityManagerFactory entityManagerFactory;

	public CategoriaDaoHibernate(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public List<CategoriaResponseDTO> retornaCategorias() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			String jpql = "SELECT new main.java.gerenciador.estoque.caixa.dtos.categorias.CategoriaResponseDTO(c.id, c.nome) " +
					"FROM Categoria c";

			return entityManager.createQuery(jpql, CategoriaResponseDTO.class).getResultList();

		} catch (Exception erro) {
			System.err.println("Erro ao recuperar categorias: " + erro.getMessage());
			return Collections.emptyList();

		} finally {
			entityManager.close();
		}
	}


}
