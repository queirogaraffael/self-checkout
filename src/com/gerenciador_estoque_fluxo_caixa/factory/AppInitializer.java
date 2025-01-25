package com.gerenciador_estoque_fluxo_caixa.factory;

import com.gerenciador_estoque_fluxo_caixa.hibernateConnection.EntityManagerFactoryService;

public class AppInitializer {

    public static ControllerFactory initControllerFactory(EntityManagerFactoryService entityManagerFactoryService) {
        DaoFactory daoFactory = new DaoFactory(entityManagerFactoryService.entityManagerFactory());
        return new ControllerFactory(daoFactory);
    }
}

