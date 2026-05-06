package gerenciador.config;

import gerenciador.controller.AutoatendimentoController;
import gerenciador.controller.EstoqueController;
import gerenciador.infrastructure.RepositoryFactory;
import gerenciador.model.NotaFiscal;
import gerenciador.service.CategoriaService;
import gerenciador.service.FinalizarCompraService;
import gerenciador.service.ItemVendaService;
import gerenciador.service.ProdutoService;
import gerenciador.service.VendaService;

public class ControllerRegistry {

    private final RepositoryFactory repositoryFactory;
    private NotaFiscal notaFiscal;

    private ItemVendaService itemVendaService;
    private CategoriaService categoriaService;
    private ProdutoService produtoService;
    private VendaService vendaService;
    private FinalizarCompraService finalizarCompraService;

    public ControllerRegistry(RepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
    }

    public NotaFiscal createNotaFiscal() {
        if (notaFiscal == null) {
            notaFiscal = new NotaFiscal();
        }
        return notaFiscal;
    }

    public ItemVendaService createItemVendaService() {
        if (itemVendaService == null) {
            itemVendaService = new ItemVendaService(repositoryFactory.createItemVendaRepository(),
                    repositoryFactory.createProdutoRepository());
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
        return new EstoqueController(createNotaFiscal(), createItemVendaService(), createCategoriaService(),
                createProdutoService(), createVendaService());
    }

    public FinalizarCompraService createFinalizarCompraService() {
        if (finalizarCompraService == null) {
            finalizarCompraService = new FinalizarCompraService(repositoryFactory.getEntityManagerFactory());
        }
        return finalizarCompraService;
    }

    public AutoatendimentoController createAutoatendimentoController() {
        return new AutoatendimentoController(createNotaFiscal(), createItemVendaService(), createProdutoService(),
                createFinalizarCompraService());
    }
}
