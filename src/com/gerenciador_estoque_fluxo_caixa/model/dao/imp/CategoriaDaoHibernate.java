package com.gerenciador_estoque_fluxo_caixa.model.dao.imp;

import com.gerenciador_estoque_fluxo_caixa.dtos.categorias.CategoriaResponseDTO;
import com.gerenciador_estoque_fluxo_caixa.model.dao.CategoriaDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.*;
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
			String jpql = "SELECT new com.gerenciador_estoque_fluxo_caixa.dtos.categorias.CategoriaResponseDTO(c.id, c.nome) " +
					"FROM Categoria c";

			return entityManager.createQuery(jpql, CategoriaResponseDTO.class).getResultList();

		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro ao recuperar categorias: " + erro.getMessage());
			return Collections.emptyList();

		} finally {
			entityManager.close();
		}
	}


}
