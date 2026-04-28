package gerenciador.factory;


import gerenciador.controller.CaixaController;
import gerenciador.controller.EstoqueController;
import gerenciador.model.NotaFiscal;
import gerenciador.service.CategoriaService;
import gerenciador.service.ItemVendaService;
import gerenciador.service.ProdutoService;
import gerenciador.service.VendaService;

public class ControllerFactory {

    private final RepositoryFactory repositoryFactory;
    private NotaFiscal notaFiscal;

    private ItemVendaService itemVendaService;
    private CategoriaService categoriaService;
    private ProdutoService produtoService;
    private VendaService vendaService;

    public ControllerFactory(RepositoryFactory daoFactory) {
        this.repositoryFactory = daoFactory;
    }

    public NotaFiscal createNotaFiscal() {
        if (notaFiscal == null) {
            notaFiscal = new NotaFiscal();
        }
        return notaFiscal;
    }

    public ItemVendaService createItemVendaService() {
        if (itemVendaService == null) {
            itemVendaService = new ItemVendaService(repositoryFactory.createItemVendaRepository(), repositoryFactory.createProdutoRepository());
        }
        return itemVendaService;
    }

    public CategoriaService createCategoriaService() {
        if (categoriaService == null) {
            categoriaService = new CategoriaService(repositoryFactory.createCategoriaRepository());
        }
        return categoriaService;
    }

    public ProdutoService createProdutoService() {
        if (produtoService == null) {
            produtoService = new ProdutoService(repositoryFactory.createProdutoRepository());
        }
        return produtoService;
    }

    public VendaService createVendaService() {
        if (vendaService == null) {
            vendaService = new VendaService(repositoryFactory.createVendaRepository());
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

