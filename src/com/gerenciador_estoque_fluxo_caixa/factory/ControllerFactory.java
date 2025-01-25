package com.gerenciador_estoque_fluxo_caixa.factory;

import com.gerenciador_estoque_fluxo_caixa.controllers.CaixaController;
import com.gerenciador_estoque_fluxo_caixa.controllers.EstoqueController;
import com.gerenciador_estoque_fluxo_caixa.model.domain.NotaFiscal;
import com.gerenciador_estoque_fluxo_caixa.service.CategoriaService;
import com.gerenciador_estoque_fluxo_caixa.service.ItemVendaService;
import com.gerenciador_estoque_fluxo_caixa.service.ProdutoService;
import com.gerenciador_estoque_fluxo_caixa.service.VendaService;

public class ControllerFactory {

    private final DaoFactory daoFactory;
    private NotaFiscal notaFiscal;

    private ItemVendaService itemVendaService;
    private CategoriaService categoriaService;
    private ProdutoService produtoService;
    private VendaService vendaService;

    public ControllerFactory(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public NotaFiscal createNotaFiscal() {
        if (notaFiscal == null) {
            notaFiscal = new NotaFiscal();
        }
        return notaFiscal;
    }

    public ItemVendaService createItemVendaService() {
        if (itemVendaService == null) {
            itemVendaService = new ItemVendaService(daoFactory.createItemVendaDao());
        }
        return itemVendaService;
    }

    public CategoriaService createCategoriaService() {
        if (categoriaService == null) {
            categoriaService = new CategoriaService(daoFactory.createCategoriaDao());
        }
        return categoriaService;
    }

    public ProdutoService createProdutoService() {
        if (produtoService == null) {
            produtoService = new ProdutoService(daoFactory.createProdutoDao());
        }
        return produtoService;
    }

    public VendaService createVendaService() {
        if (vendaService == null) {
            vendaService = new VendaService(daoFactory.createVendaDao());
        }
        return vendaService;
    }

    public EstoqueController createEstoqueController() {
        return new EstoqueController(createNotaFiscal(), createItemVendaService(), createCategoriaService(), createProdutoService(), createVendaService());
    }

    public CaixaController createCaixaController() {
        return new CaixaController(createNotaFiscal(), createItemVendaService(), createCategoriaService(), createProdutoService(), createVendaService());
    }
}

