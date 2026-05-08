package gerenciador.infrastructure.repository.hibernate;

import gerenciador.dto.produto.*;
import gerenciador.infrastructure.repository.ProdutoRepository;
import gerenciador.model.Produto;
import gerenciador.model.Categoria;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collections;
import java.util.List;

import javax.persistence.OptimisticLockException;
import javax.persistence.RollbackException;
import gerenciador.infrastructure.exception.ProdutoModificadoConcorrentementeException;

public class ProdutoRepositoryHibernate implements ProdutoRepository {

    private final EntityManagerFactory entityManagerFactory;

    public ProdutoRepositoryHibernate(EntityManagerFactory entityManagerFactory) {
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
            
            Categoria categoria = entityManager.find(Categoria.class, produtoCreateDTO.getIdCategoria());
            produto.setCategoria(categoria);

            entityManager.persist(produto);
            entityManager.getTransaction().commit();
            return new ProdutoCreateDTO(produto.getCodigoDeBarra(), produto.getNome(), produto.getPreco(), produto.getQuantidade(), categoria.getId(), categoria.getNome());

        } catch (Exception erro) {
            entityManager.getTransaction().rollback();
            System.err.println("Problemas em adicionar o produto: " + erro.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }


    public boolean atualizaProduto(Produto produto) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            entityManager.merge(produto);
            entityManager.getTransaction().commit();
            return true;

        } catch (OptimisticLockException erro) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            throw new ProdutoModificadoConcorrentementeException("O produto foi alterado por outro usuário.");
        } catch (RollbackException erro) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            if (erro.getCause() instanceof OptimisticLockException) {
                throw new ProdutoModificadoConcorrentementeException("O produto foi alterado por outro usuário.", erro);
            }
            System.err.println("Problema na atualizacao do produto." + erro.getMessage());
            return false;
        } catch (Exception erro) {
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            System.err.println("Problema na atualizacao do produto." + erro.getMessage());
            return false;
        } finally {
            if (entityManager.isOpen()) entityManager.close();
        }

    }


    public ProdutoDTO retornaProdutoDTOPorCodigo(String codigo) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            String jpql = "SELECT new gerenciador.dto.produto.ProdutoDTO(" +
                    "p.codigoDeBarra, p.nome, p.preco, p.quantidade, p.categoria) " +
                    "FROM Produto p " +
                    "WHERE p.codigoDeBarra = :codigoDeBarra";

            List<ProdutoDTO> produtos = entityManager.createQuery(jpql, ProdutoDTO.class)
                    .setParameter("codigoDeBarra", codigo)
                    .getResultList();

            return produtos.stream().findFirst().orElse(null);

        } catch (Exception erro) {
            System.err.println("Erro ao buscar produto por código: " + codigo);
            erro.printStackTrace();
            return null;
        } finally {
            entityManager.close();
        }
    }


    @Override
    public ProdutoAtualizarQuantidadeDTO retornaProdutoAtualizarQuantidadeDTO(String codigo) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            String jpql = "SELECT new gerenciador.dto.produto.ProdutoAtualizarQuantidadeDTO(p.quantidade) " +
                    "FROM Produto p " +
                    "WHERE p.codigoDeBarra = :codigoDeBarra";

            List<ProdutoAtualizarQuantidadeDTO> produtos = entityManager.createQuery(jpql, ProdutoAtualizarQuantidadeDTO.class)
                    .setParameter("codigoDeBarra", codigo)
                    .getResultList();

            if (produtos.isEmpty()) {
                return null;
            }

            return produtos.stream().findFirst().orElse(null);


        } catch (Exception erro) {
            System.err.println("Problemas ao buscar por produto" + erro.getMessage());
            return null;
        } finally {
            entityManager.close();
        }

    }

    public Produto retornaProdutoPorCodigo(String codigo) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            String jpql = "SELECT p FROM Produto p " +
                    "WHERE p.codigoDeBarra = :codigoDeBarra";

            List<Produto> produtos = entityManager.createQuery(jpql, Produto.class)
                    .setParameter("codigoDeBarra", codigo)
                    .getResultList();

            if (produtos.isEmpty()) {
                return null;
            }

            return produtos.get(0);

        } catch (Exception erro) {
            System.err.println("Problemas ao buscar por produto" + erro.getMessage());
            return null;
        } finally {
            entityManager.close();
        }
    }

    public List<ProdutoResponseDTO> retornaProdutosPorCategoria(Integer idCategoria) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            String jpql = "SELECT new gerenciador.dto.produto.ProdutoResponseDTO(p.codigoDeBarra, p.nome) "
                    + "FROM Produto p WHERE p.categoria.id = :categoria";

            return entityManager
                    .createQuery(jpql, ProdutoResponseDTO.class)
                    .setParameter("categoria", idCategoria).getResultList();

        } catch (Exception erro) {
            System.err.println("Erro ao tentar gerar relatorio dos produtos: " + erro.getMessage());
            return Collections.emptyList();
        } finally {
            entityManager.close();
        }
    }

    public boolean haProduto() {

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        try {
            Long quantidade = entityManager.createQuery("SELECT COUNT(p) FROM Produto p", Long.class).getSingleResult();

            return quantidade == 0;

        } catch (Exception erro) {
            System.err.println("Erro ao tentar verificar se tabela de produtos esta vazia: " + erro.getMessage());
            return false;
        } finally {
            entityManager.close();
        }

    }


    public List<ProdutoBaixoEstoqueResponseDTO> retornaProdutosEstoqueBaixo() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            String jpql = "SELECT new gerenciador.dto.produto.ProdutoBaixoEstoqueResponseDTO(p.codigoDeBarra, p.nome, p.quantidade) "
                    + "FROM Produto p WHERE p.quantidade <= :quantidade";

            return entityManager
                    .createQuery(jpql, ProdutoBaixoEstoqueResponseDTO.class)
                    .setParameter("quantidade", 10).getResultList();

        } catch (Exception erro) {
            System.err.println("Erro ao gerar relatorio de produtos com baixo estoque: " + erro.getMessage());
            return Collections.emptyList();
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
            System.err.println("Problemas ao buscar por produto" + erro.getMessage());
            return false;
        } finally {
            entityManager.close();
        }
    }

}