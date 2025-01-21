package com.gerenciador_estoque_fluxo_caixa.hibernateConnection;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryService {

	private static EntityManagerFactory entityManagerFactory;

	public static EntityManagerFactory entityManagerFactory() {

		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory("persistencia");
		}
		return entityManagerFactory;
	}

	public static void inicializarEntityManagerFactory() {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory("persistencia");
		}
	}

	public static void fechaEntityManagerFactory() {

		if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
			entityManagerFactory.close();
		}
	}

}
