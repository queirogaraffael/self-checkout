package gerenciador.infrastructure.repository.hibernate;

import gerenciador.dto.categoria.CategoriaResponseDTO;
import gerenciador.infrastructure.repository.CategoriaRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collections;
import java.util.List;

public class CategoriaRepositoryHibernate implements CategoriaRepository {

	private final EntityManagerFactory entityManagerFactory;

	public CategoriaRepositoryHibernate(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public List<CategoriaResponseDTO> retornaCategorias() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			String jpql = "SELECT new gerenciador.dto.categoria.CategoriaResponseDTO(c.id, c.nome) " +
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
