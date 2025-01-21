package com.gerenciador_estoque_fluxo_caixa.model.dao.imp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;

import com.gerenciador_estoque_fluxo_caixa.model.dao.ItemVendaDao;
import com.gerenciador_estoque_fluxo_caixa.model.entities.ItemVenda;
import com.gerenciador_estoque_fluxo_caixa.model.entities.Venda;

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
			JOptionPane.showMessageDialog(null, "Problemas em adicionar item." + erro);
		} finally {
			entityManager.close();
		}

	}

	public Set<ItemVenda> retornaItensVenda(Venda venda) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {

			List<ItemVenda> itens = entityManager
					.createQuery("SELECT p FROM ItemVenda p WHERE p.id.venda = :venda", ItemVenda.class)
					.setParameter("venda", venda).getResultList();

			Set<ItemVenda> itensVenda = new HashSet<>(itens);
			return itensVenda;

		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro ao retornar itens venda." + erro);
			return null;
		} finally {
			entityManager.close();
		}

	}

}
