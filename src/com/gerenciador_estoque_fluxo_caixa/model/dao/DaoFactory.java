package com.gerenciador_estoque_fluxo_caixa.model.dao;

import com.gerenciador_estoque_fluxo_caixa.hibernateConnection.EntityManagerFactoryService;
import com.gerenciador_estoque_fluxo_caixa.model.dao.imp.CategoriaDaoHibernate;
import com.gerenciador_estoque_fluxo_caixa.model.dao.imp.ItemVendaDaoHibernate;
import com.gerenciador_estoque_fluxo_caixa.model.dao.imp.ProdutoDaoHibernate;
import com.gerenciador_estoque_fluxo_caixa.model.dao.imp.VendaDaoHibernate;

public class DaoFactory {

	public static CategoriaDao createCategoriaDao() {
		return new CategoriaDaoHibernate(EntityManagerFactoryService.entityManagerFactory());
	}

	public static ItemVendaDao createItemVendaDao() {
		return new ItemVendaDaoHibernate(EntityManagerFactoryService.entityManagerFactory());
	}

	public static ProdutoDao createProdutoDao() {
		return new ProdutoDaoHibernate(EntityManagerFactoryService.entityManagerFactory());
	}

	public static VendaDao createVendaDao() {
		return new VendaDaoHibernate(EntityManagerFactoryService.entityManagerFactory());
	}

}
