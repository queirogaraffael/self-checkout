package com.gerenciador_estoque_fluxo_caixa.factory;


import com.gerenciador_estoque_fluxo_caixa.model.dao.CategoriaDao;
import com.gerenciador_estoque_fluxo_caixa.model.dao.ItemVendaDao;
import com.gerenciador_estoque_fluxo_caixa.model.dao.ProdutoDao;
import com.gerenciador_estoque_fluxo_caixa.model.dao.VendaDao;
import com.gerenciador_estoque_fluxo_caixa.model.dao.imp.CategoriaDaoHibernate;
import com.gerenciador_estoque_fluxo_caixa.model.dao.imp.ItemVendaDaoHibernate;
import com.gerenciador_estoque_fluxo_caixa.model.dao.imp.ProdutoDaoHibernate;
import com.gerenciador_estoque_fluxo_caixa.model.dao.imp.VendaDaoHibernate;

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
