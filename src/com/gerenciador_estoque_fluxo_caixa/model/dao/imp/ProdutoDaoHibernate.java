package com.gerenciador_estoque_fluxo_caixa.model.dao.imp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.swing.JOptionPane;

import com.gerenciador_estoque_fluxo_caixa.dtos.produtos.ProdutoCreateDTO;
import com.gerenciador_estoque_fluxo_caixa.dtos.produtos.ProdutoDTO;
import com.gerenciador_estoque_fluxo_caixa.model.dao.ProdutoDao;
import com.gerenciador_estoque_fluxo_caixa.model.entities.Produto;

public class ProdutoDaoHibernate implements ProdutoDao {

	private EntityManagerFactory entityManagerFactory;

	public ProdutoDaoHibernate(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public ProdutoCreateDTO adicionaProduto(ProdutoCreateDTO produtoCreateDTO) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {
			Produto produto = new Produto();

			produto.setCodigoDeBarra(produtoCreateDTO.getCodigoDeBarra());
			produto.setNome(produtoCreateDTO.getNome());
			produto.setPreco(produtoCreateDTO.getPreco());
			produto.setQuantidade(produtoCreateDTO.getQuantidade());
			produto.setCategoria(produtoCreateDTO.getCategoria());

			entityManager.persist(produto);
			entityManager.getTransaction().commit();
			return new ProdutoCreateDTO(produto.getCodigoDeBarra(), produto.getNome(), produto.getPreco(), produto.getQuantidade(), produto.getCategoria());

		} catch (Exception erro) {
			entityManager.getTransaction().rollback();
			JOptionPane.showMessageDialog(null, "Problemas em adicionar o produto: " + erro.getMessage());
			return null;
		} finally {
			entityManager.close();
		}
	}



	public void atualizaProduto(Produto produto) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {
			entityManager.merge(produto);
			entityManager.getTransaction().commit();
		} catch (Exception erro) {
			entityManager.getTransaction().rollback();
			JOptionPane.showMessageDialog(null, "Problema na atualizacao do produto." + erro);
		} finally {
			entityManager.close();
		}

	}

	public void removeProduto(String codigo) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {
			Produto produto = entityManager.find(Produto.class, codigo);

			if (produto != null) {
				entityManager.remove(produto);
				entityManager.getTransaction().commit();
			} else {
				JOptionPane.showMessageDialog(null, "Produto nao existe");
			}

		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Problemas ao remover produto" + erro);
		} finally {
			entityManager.close();
		}

	}

	public ProdutoDTO retornaProdutoPorCodigo(String codigo) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			String jpql = "SELECT new com.gerenciador_estoque_fluxo_caixa.dtos.produtos.ProdutoDTO(p.codigoDeBarra, p.nome, p.preco) " +
					"FROM Produto p " +
					"WHERE p.codigoDeBarra = :codigoDeBarra";

			List<ProdutoDTO> produtos = entityManager.createQuery(jpql, ProdutoDTO.class)
					.setParameter("codigoDeBarra", codigo)
					.getResultList();

			if (produtos.isEmpty()) {
				return null;
			}

			return produtos.get(0);


		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Problemas ao buscar por produto" + erro);
			return null;
		} finally {
			entityManager.close();
		}
	}

	public String retornaProdutosPorCategoria(Integer idCategoria) {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {

			// nao retorna todos os produtos

			List<Produto> produtos = entityManager
					.createQuery("SELECT p FROM Produto p WHERE p.categoria = :categoria", Produto.class)
					.setParameter("categoria", idCategoria).getResultList();

			return produtos;


		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro ao tentar gerar relatorio dos produtos: " + erro);
		} finally {
			entityManager.close();
		}
		return "";
	}

	public boolean tabelaProdutoEstaVazia() {

		EntityManager entityManager = entityManagerFactory.createEntityManager();

		entityManager.getTransaction().begin();

		try {
			Long quantidade = entityManager.createQuery("SELECT COUNT(*) FROM Produto", Long.class).getSingleResult();

			if (quantidade == 0) {
				return true;
			} else {
				return false;
			}

		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se tabela de produtos esta vazia: " + erro);
			return false;
		} finally {
			entityManager.close();
		}

	}



	public String retornaProdutosEstoqueBaixo() {

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		try {

			return entityManager
					.createQuery("SELECT p FROM Produto p WHERE p.quantidade <= 10", Produto.class).getResultList();

		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro ao gerar relatorio de produtos com baixo estoque: " + erro);
			return "";
		} finally {
			entityManager.close();
		}

	}

	@Override
	public Boolean haProdutoComMesmoCodigoBarra(String codigo) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {

			String jpql = "SELECT COUNT(p) FROM Produto p " +
					"WHERE p.codigoDeBarra = :codigoDeBarra";

			Long quantidade = entityManager.createQuery(jpql, Long.class)
					.setParameter("codigoDeBarra", codigo)
					.getSingleResult();

			return quantidade > 0;

		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Problemas ao buscar por produto" + erro);
			return false;
		} finally {
			entityManager.close();
		}
	}

}