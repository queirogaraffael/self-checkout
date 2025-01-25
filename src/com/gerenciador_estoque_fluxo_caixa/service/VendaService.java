package com.gerenciador_estoque_fluxo_caixa.service;

import com.gerenciador_estoque_fluxo_caixa.model.dao.VendaDao;
import com.gerenciador_estoque_fluxo_caixa.model.dao.imp.VendaDaoHibernate;

import javax.persistence.EntityManagerFactory;

public class VendaService {

    private final VendaDao vendaDao;

    public VendaService(VendaDao vendaDao) {
        this.vendaDao = vendaDao;
    }
}
