package gerenciador.infrastructure.repository.hibernate;


import gerenciador.infrastructure.repository.ItemVendaRepository;
import gerenciador.model.ItemVenda;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemVendaRepositoryHibernate implements ItemVendaRepository {

    private final EntityManagerFactory entityManagerFactory;

    public ItemVendaRepositoryHibernate(EntityManagerFactory entityManagerFactory) {
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

    public Set<ItemVenda> retornaItensVenda(Integer codigoVenda) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            String jpql = "SELECT p FROM ItemVenda p WHERE p.id.venda.codigo = :codigoVenda";

            List<ItemVenda> itens = entityManager
                    .createQuery(jpql, ItemVenda.class)
                    .setParameter("codigoVenda", codigoVenda)
                    .getResultList();

            return new HashSet<>(itens);

        } catch (Exception erro) {
            System.err.println("Erro ao retornar itens venda: " + erro);
            return Collections.emptySet();
        } finally {
            entityManager.close();
        }
    }


}
