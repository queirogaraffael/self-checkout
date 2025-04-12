package main.java.gerenciador.estoque.caixa.factory;


import main.java.gerenciador.estoque.caixa.controllers.CaixaController;
import main.java.gerenciador.estoque.caixa.controllers.EstoqueController;
import main.java.gerenciador.estoque.caixa.model.domain.NotaFiscal;
import main.java.gerenciador.estoque.caixa.service.CategoriaService;
import main.java.gerenciador.estoque.caixa.service.ItemVendaService;
import main.java.gerenciador.estoque.caixa.service.ProdutoService;
import main.java.gerenciador.estoque.caixa.service.VendaService;

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
            itemVendaService = new ItemVendaService(daoFactory.createItemVendaDao(), daoFactory.createProdutoDao());
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

