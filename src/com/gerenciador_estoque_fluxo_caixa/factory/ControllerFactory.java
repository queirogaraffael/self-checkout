package com.gerenciador_estoque_fluxo_caixa.factory;

import com.gerenciador_estoque_fluxo_caixa.controllers.CaixaController;
import com.gerenciador_estoque_fluxo_caixa.controllers.EstoqueController;
import com.gerenciador_estoque_fluxo_caixa.hibernateConnection.EntityManagerFactoryService;
import com.gerenciador_estoque_fluxo_caixa.model.domain.NotaFiscal;
import com.gerenciador_estoque_fluxo_caixa.service.CategoriaService;
import com.gerenciador_estoque_fluxo_caixa.service.ItemVendaService;
import com.gerenciador_estoque_fluxo_caixa.service.ProdutoService;
import com.gerenciador_estoque_fluxo_caixa.service.VendaService;

public class ControllerFactory {

    private final EntityManagerFactoryService entityManagerFactoryService;
    private final NotaFiscal notaFiscal;

    public ControllerFactory(EntityManagerFactoryService entityManagerFactoryService, NotaFiscal notaFiscal) {
        this.entityManagerFactoryService = entityManagerFactoryService;
        this.notaFiscal = notaFiscal;
    }

    public EstoqueController createEstoqueController() {
        ItemVendaService itemVendaService = new ItemVendaService(entityManagerFactoryService.entityManagerFactory());
        CategoriaService categoriaService = new CategoriaService(entityManagerFactoryService.entityManagerFactory());
        ProdutoService produtoService = new ProdutoService(entityManagerFactoryService.entityManagerFactory());
        VendaService vendaService = new VendaService(entityManagerFactoryService.entityManagerFactory());
        return new EstoqueController(notaFiscal, itemVendaService, categoriaService, produtoService, vendaService);
    }

    public CaixaController createCaixaController() {
        ItemVendaService itemVendaService = new ItemVendaService(entityManagerFactoryService.entityManagerFactory());
        CategoriaService categoriaService = new CategoriaService(entityManagerFactoryService.entityManagerFactory());
        ProdutoService produtoService = new ProdutoService(entityManagerFactoryService.entityManagerFactory());
        VendaService vendaService = new VendaService(entityManagerFactoryService.entityManagerFactory());
        return new CaixaController(notaFiscal, itemVendaService, categoriaService, produtoService, vendaService);
    }
}

