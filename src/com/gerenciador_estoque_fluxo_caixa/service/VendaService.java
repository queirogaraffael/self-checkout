package com.gerenciador_estoque_fluxo_caixa.service;

import com.gerenciador_estoque_fluxo_caixa.model.dao.VendaDao;
import com.gerenciador_estoque_fluxo_caixa.model.dao.imp.VendaDaoHibernate;
import com.gerenciador_estoque_fluxo_caixa.model.entities.Venda;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;

public class VendaService {

    private final VendaDao vendaDao;

    public VendaService(VendaDao vendaDao) {
        this.vendaDao = vendaDao;
    }

    public boolean tabelaVendaEstaVazia() {
        return false;
    }

    public String geraRelatioVendas() {
        return null;
    }

    public Venda retornaVendaPorCodigo(Integer codigo) {
        return null;
    }

    public void adicionaVenda(Venda venda) {
    }

    public void atualizaVenda(Venda venda) {

    }

    public String geraRelatiorioVendasPorData(LocalDate data) {
        return null;
    }
}
