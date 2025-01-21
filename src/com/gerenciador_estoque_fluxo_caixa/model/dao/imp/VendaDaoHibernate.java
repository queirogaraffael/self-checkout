package com.gerenciador_estoque_fluxo_caixa.model.dao.imp;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;

import com.gerenciador_estoque_fluxo_caixa.model.dao.VendaDao;
import com.gerenciador_estoque_fluxo_caixa.model.entities.Venda;

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

	public void atualizaVenda(Venda venda) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {
			entityManager.merge(venda);
			entityManager.getTransaction().commit();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Problemas em atualizar venda" + e.getMessage());
		} finally {
			entityManager.close();
		}

	}

	public Venda retornaVendaPorCodigo(Integer codigo) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			return entityManager.find(Venda.class, codigo);

		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Problemas ao buscar por venda" + erro);
			return null;

		} finally {
			entityManager.close();
		}
	}

	public String geraRelatioVendas() {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {

			List<Venda> vendas = entityManager.createQuery("FROM Venda", Venda.class).getResultList();

			StringBuilder sb = new StringBuilder();

			for (Venda venda : vendas) {
				sb.append(venda + "\n");
			}
			return sb.toString();

		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro ao tentar gerar relatorio de vendas: " + erro);
			return "";
		} finally {
			entityManager.close();
		}

	}

	public boolean tabelaVendaEstaVazia() {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {

			Long quantidade = entityManager.createQuery("SELECT COUNT(*) FROM Venda", Long.class).getSingleResult();
			if (quantidade == 0) {
				return true;
			} else {
				return false;
			}

		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se tabela de vendas esta vazia: " + erro);
			return false;
		} finally {
			entityManager.close();
		}

	}

	public String geraRelatiorioVendasPorData(LocalDate data) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {

			List<Venda> vendas = entityManager
					.createQuery("SELECT p FROM Venda p WHERE CAST(p.dataHora AS date) = :data", Venda.class)
					.setParameter("data", Date.valueOf(data)).getResultList();

			Double total = 0.0;

			StringBuilder sb = new StringBuilder();
			for (Venda venda : vendas) {
				sb.append(venda + "\n");
				total += venda.getTotal();
			}

			sb.append("Total: ").append(total);
			return sb.toString();

		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro em buscar vendas por data: " + erro);
			return "";
		} finally {
			entityManager.close();
		}

	}

}
