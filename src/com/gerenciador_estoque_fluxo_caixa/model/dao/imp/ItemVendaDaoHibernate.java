package com.gerenciador_estoque_fluxo_caixa.model.dao.imp;

import com.gerenciador_estoque_fluxo_caixa.dtos.itemvenda.ItemVendaDTO;
import com.gerenciador_estoque_fluxo_caixa.model.dao.ItemVendaDao;
import com.gerenciador_estoque_fluxo_caixa.model.entities.ItemVenda;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ItemVendaDaoHibernate implements ItemVendaDao {

	private EntityManagerFactory entityManagerFactory;

	public ItemVendaDaoHibernate(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public void adicionaItemVenda(ItemVenda itemVenda) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {
			entityManager.persist(itemVenda);
			entityManager.getTransaction().commit();

		} catch (Exception erro) {
			System.err.println("Problemas em adicionar item." + erro);
		} finally {
			entityManager.close();
		}

	}

	public Set<ItemVendaDTO> retornaItensVenda(Integer codigoVenda) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {
			String jpql = "SELECT p FROM ItemVenda p WHERE p.id.venda.codigo = :codigoVenda";

			List<ItemVenda> itens = entityManager
					.createQuery(jpql, ItemVenda.class)
					.setParameter("codigoVenda", codigoVenda)
					.getResultList();

			return itens.stream()
					.map(ItemVendaDTO::new)
					.collect(Collectors.toSet());

		} catch (Exception erro) {
			System.err.println("Erro ao retornar itens venda. " + erro);
			return Collections.emptySet();
		} finally {
			entityManager.close();
		}
	}


}
