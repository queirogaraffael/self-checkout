package gerenciador.infrastructure;


import gerenciador.infrastructure.repository.CategoriaRepository;
import gerenciador.infrastructure.repository.ItemVendaRepository;
import gerenciador.infrastructure.repository.ProdutoRepository;
import gerenciador.infrastructure.repository.VendaRepository;
import gerenciador.infrastructure.repository.hibernate.CategoriaRepositoryHibernate;
import gerenciador.infrastructure.repository.hibernate.ItemVendaRepositoryHibernate;
import gerenciador.infrastructure.repository.hibernate.ProdutoRepositoryHibernate;
import gerenciador.infrastructure.repository.hibernate.VendaRepositoryHibernate;

import javax.persistence.EntityManagerFactory;

public class RepositoryFactory {

    private final EntityManagerFactory entityManagerFactory;

    public RepositoryFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public CategoriaRepository createCategoriaRepository() {
        return new CategoriaRepositoryHibernate(entityManagerFactory);
    }

    public ItemVendaRepository createItemVendaRepository() {
        return new ItemVendaRepositoryHibernate(entityManagerFactory);
    }

    public ProdutoRepository createProdutoRepository() {
        return new ProdutoRepositoryHibernate(entityManagerFactory);
    }

    public VendaRepository createVendaRepository() {
        return new VendaRepositoryHibernate(entityManagerFactory);
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
}
