package com.gerenciador_estoque_fluxo_caixa.service;

import com.gerenciador_estoque_fluxo_caixa.model.dao.CategoriaDao;
import com.gerenciador_estoque_fluxo_caixa.model.dao.imp.CategoriaDaoHibernate;

import javax.persistence.EntityManagerFactory;

public class CategoriaService {

    private final CategoriaDao categoriaDao;

    public CategoriaService(EntityManagerFactory entityManagerFactory) {
        this.categoriaDao = new CategoriaDaoHibernate(entityManagerFactory);
    }

}
