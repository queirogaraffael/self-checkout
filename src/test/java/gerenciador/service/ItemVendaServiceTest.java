package gerenciador.service;

import gerenciador.infrastructure.repository.ItemVendaRepository;
import gerenciador.infrastructure.repository.ProdutoRepository;
import gerenciador.model.ItemVenda;
import gerenciador.model.Produto;
import gerenciador.service.ItemVendaService;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ItemVendaServiceTest {

    private ItemVendaRepository itemVendaDao;
    private ProdutoRepository produtoDao;
    private ItemVendaService itemVendaService;

    @Before
    public void setUp() {
        itemVendaDao = mock(ItemVendaRepository.class);
        produtoDao = mock(ProdutoRepository.class);
        itemVendaService = new ItemVendaService(itemVendaDao, produtoDao);
    }

    @Test
    public void testContemProduto() {
        // Cenário
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setCodigoDeBarra("12345");
        produto.setNome("Produto A");
        produto.setPreco(10.0);
        produto.setQuantidade(10);
        produto.setCategoria(null);

        ItemVenda itemVenda = new ItemVenda(produto, 2);
        Set<ItemVenda> listaCompras = new HashSet<>();
        listaCompras.add(itemVenda);

        // Ação
        boolean resultado = ItemVendaService.contemProduto(listaCompras, "12345");

        // Validação
        assertTrue(resultado);
    }

    @Test
    public void testSomaPrecos() {
        // Cenário
        Produto produtoA = new Produto();
        produtoA.setId(1L);
        produtoA.setCodigoDeBarra("12345");
        produtoA.setNome("Produto A");
        produtoA.setPreco(10.0);
        produtoA.setQuantidade(10);
        produtoA.setCategoria(null);

        Produto produtoB = new Produto();
        produtoB.setId(2L);
        produtoB.setCodigoDeBarra("67890");
        produtoB.setNome("Produto B");
        produtoB.setPreco(15.0);
        produtoB.setQuantidade(5);
        produtoB.setCategoria(null);

        ItemVenda itemVendaA = new ItemVenda(produtoA, 2);
        ItemVenda itemVendaB = new ItemVenda(produtoB, 1);
        Set<ItemVenda> listaCompras = new HashSet<>();
        listaCompras.add(itemVendaA);
        listaCompras.add(itemVendaB);

        // Ação
        Double total = ItemVendaService.somaPrecos(listaCompras);

        // Validação
        assertEquals(35.0, total, 0.01);
    }

    @Test
    public void testRetornaItemVendaPeloCodigo() {
        // Cenário
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setCodigoDeBarra("12345");
        produto.setNome("Produto A");
        produto.setPreco(10.0);
        produto.setQuantidade(10);
        produto.setCategoria(null);

        ItemVenda itemVenda = new ItemVenda(produto, 2);
        Set<ItemVenda> listaCompras = new HashSet<>();
        listaCompras.add(itemVenda);

        // Ação
        ItemVenda itemResultado = ItemVendaService.retornaItemVendaPeloCodigo(listaCompras, "12345");

        // Validação
        assertNotNull(itemResultado);
        assertEquals(produto, itemResultado.getProduto());
    }

    @Test
    public void testRetornaItensVenda() {
        // Cenário
        Integer codigoVenda = 1;
        Set<ItemVenda> itensVenda = new HashSet<>();
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setCodigoDeBarra("12345");
        produto.setNome("Produto A");
        produto.setPreco(10.0);
        produto.setQuantidade(10);
        produto.setCategoria(null);

        ItemVenda itemVenda = new ItemVenda(produto, 2);
        itensVenda.add(itemVenda);

        when(itemVendaDao.retornaItensVenda(codigoVenda)).thenReturn(itensVenda);

        // Ação
        Set<ItemVenda> resultado = itemVendaService.retornaItensVenda(codigoVenda);

        // Validação
        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(itemVenda));
    }

    @Test
    public void testAdicionaItemVenda() {
        // Cenário
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setCodigoDeBarra("12345");
        produto.setNome("Produto A");
        produto.setPreco(10.0);
        produto.setQuantidade(10);
        produto.setCategoria(null);
        ItemVenda itemVenda = new ItemVenda(produto, 2);

        // Ação
        itemVendaService.adicionaItemVenda(itemVenda);

        // Validação
        verify(itemVendaDao, times(1)).adicionaItemVenda(itemVenda);
    }

    @Test
    public void testGeraRelatorioItemVenda() {
        // Cenário
        Produto produtoA = new Produto();
        produtoA.setId(1L);
        produtoA.setCodigoDeBarra("12345");
        produtoA.setNome("Produto A");
        produtoA.setPreco(10.0);
        produtoA.setQuantidade(10);
        produtoA.setCategoria(null);

        Produto produtoB = new Produto();
        produtoB.setId(2L);
        produtoB.setCodigoDeBarra("67890");
        produtoB.setNome("Produto B");
        produtoB.setPreco(15.0);
        produtoB.setQuantidade(5);
        produtoB.setCategoria(null);

        ItemVenda itemVendaA = new ItemVenda(produtoA, 2);
        ItemVenda itemVendaB = new ItemVenda(produtoB, 1);
        Set<ItemVenda> itensVenda = new HashSet<>();
        itensVenda.add(itemVendaA);
        itensVenda.add(itemVendaB);

        // Ação
        String relatorio = ItemVendaService.geraRelatorioItemVenda(itensVenda);

        // Validação
        assertTrue(relatorio.contains("Codigo: 12345, nome = Produto A, Preco: 10.0 R$, Quantidade: 2"));
        assertTrue(relatorio.contains("Codigo: 67890, nome = Produto B, Preco: 15.0 R$, Quantidade: 1"));
    }

    @Test
    public void testCriaItemVendaPorCodigoProduto() {
        // Cenário
        String codigoBarra = "12345";
        Integer quantidade = 2;
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setCodigoDeBarra("12345");
        produto.setNome("Produto A");
        produto.setPreco(10.0);
        produto.setQuantidade(10);
        produto.setCategoria(null);

        when(produtoDao.retornaProdutoPorCodigo(codigoBarra)).thenReturn(produto);

        // Ação
        ItemVenda itemVenda = itemVendaService.criaItemVendaPorCodigoProduto(codigoBarra, quantidade);

        // Validação
        assertNotNull(itemVenda);
        assertEquals(produto, itemVenda.getProduto());
        assertEquals(quantidade, itemVenda.getQuantidade());
    }
}
