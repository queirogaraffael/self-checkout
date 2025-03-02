package com.gerenciador_estoque_fluxo_caixa.model.dao.imp;

import com.gerenciador_estoque_fluxo_caixa.dtos.vendas.VendaResponseDTO;
import com.gerenciador_estoque_fluxo_caixa.model.dao.VendaDao;
import com.gerenciador_estoque_fluxo_caixa.model.entities.Venda;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class VendaDaoHibernate implements VendaDao {

	private EntityManagerFactory entityManagerFactory;

	public VendaDaoHibernate(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public void adicionaVenda(Venda venda) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {
			entityManager.persist(venda);
			entityManager.getTransaction().commit();

		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Problemas em adicionar a venda" + erro);
		} finally {
			entityManager.close();
		}

	}

	public VendaResponseDTO retornaVendaDTOPorCodigo(Integer codigo) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			String jpql = "SELECT new com.gerenciador_estoque_fluxo_caixa.dtos.vendas.VendaResponseDTO(v.codigo, v.dataHora, v.total) " +
					"FROM Venda v WHERE v.codigo = :codigo";

			List<VendaResponseDTO> vendas = entityManager.createQuery(jpql, VendaResponseDTO.class)
					.setParameter("codigo", codigo)
					.getResultList();

			if (vendas.isEmpty()) {
				return null;
			}

			return vendas.stream().findFirst().orElse(null);

		} catch (Exception erro) {
			System.err.println("Problemas ao buscar por venda: " + erro.getMessage());
			return null;

		} finally {
			if (entityManager.isOpen()) {
				entityManager.close();
			}
		}
	}



	public List<VendaResponseDTO> retornaVendas() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			String jpql = "SELECT new com.gerenciador_estoque_fluxo_caixa.dtos.vendas.VendaResponseDTO(v.codigo, v.dataHora, v.total) " +
					"FROM Venda v";

			return entityManager.createQuery(jpql, VendaResponseDTO.class).getResultList();

		} catch (Exception erro) {
			System.err.println("Erro ao tentar gerar relatório de vendas: " + erro.getMessage());
			return Collections.emptyList();
		} finally {
			if (entityManager.isOpen()) {
				entityManager.close();
			}
		}
	}



	public List<VendaResponseDTO>retornaVendasPorData(LocalDate data) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {

			String jpql = "SELECT new com.gerenciador_estoque_fluxo_caixa.dtos.vendas.VendaResponseDTO(v.codigo, v.dataHora, v.total) " +
					"FROM Venda v WHERE CAST(v.dataHora AS date) = :data";

			return entityManager
					.createQuery(jpql, VendaResponseDTO.class)
					.setParameter("data", Date.valueOf(data)).getResultList();

		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro em buscar vendas por data: " + erro);
			return Collections.emptyList();
		} finally {
			entityManager.close();
		}

	}

	public boolean haVenda() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			Long quantidade = entityManager.createQuery("SELECT COUNT(*) FROM Venda", Long.class).getSingleResult();

			if (quantidade == 0) {
				return false;
			} else {
				return true;
			}

		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se tabela de vendas esta vazia: " + erro);
			return false;
		} finally {
			entityManager.close();
		}

	}

}
