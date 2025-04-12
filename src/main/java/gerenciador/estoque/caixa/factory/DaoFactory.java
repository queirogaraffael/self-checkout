package main.java.gerenciador.estoque.caixa.factory;


import main.java.gerenciador.estoque.caixa.model.dao.CategoriaDao;
import main.java.gerenciador.estoque.caixa.model.dao.ItemVendaDao;
import main.java.gerenciador.estoque.caixa.model.dao.ProdutoDao;
import main.java.gerenciador.estoque.caixa.model.dao.VendaDao;
import main.java.gerenciador.estoque.caixa.model.dao.imp.CategoriaDaoHibernate;
import main.java.gerenciador.estoque.caixa.model.dao.imp.ItemVendaDaoHibernate;
import main.java.gerenciador.estoque.caixa.model.dao.imp.ProdutoDaoHibernate;
import main.java.gerenciador.estoque.caixa.model.dao.imp.VendaDaoHibernate;

import javax.persistence.EntityManagerFactory;

public class DaoFactory {

    private final EntityManagerFactory entityManagerFactory;

    public DaoFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public CategoriaDao createCategoriaDao() {
        return new CategoriaDaoHibernate(entityManagerFactory);
    }

    public ItemVendaDao createItemVendaDao() {
        return new ItemVendaDaoHibernate(entityManagerFactory);
    }

    public ProdutoDao createProdutoDao() {
        return new ProdutoDaoHibernate(entityManagerFactory);
    }

    public VendaDao createVendaDao() {
        return new VendaDaoHibernate(entityManagerFactory);
    }
}
