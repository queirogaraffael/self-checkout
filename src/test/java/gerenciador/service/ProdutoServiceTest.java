package gerenciador.service;

import gerenciador.dto.produto.*;
import gerenciador.infrastructure.repository.ProdutoRepository;
import gerenciador.model.Categoria;
import gerenciador.model.Produto;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProdutoServiceTest {

    private ProdutoRepository produtoDao;
    private ProdutoService produtoService;

    @Before
    public void setUp() {
        produtoDao = mock(ProdutoRepository.class);
        produtoService = new ProdutoService(produtoDao);
    }

    @Test
    public void testAdicionaProduto() {
        // Cenário
        ProdutoCreateDTO produtoCreateDTO = new ProdutoCreateDTO("123456789", "Produto A", new BigDecimal("10.00"), 5, 1, "Categoria A");
        when(produtoDao.adicionaProduto(produtoCreateDTO)).thenReturn(produtoCreateDTO);

        // Ação
        ProdutoCreateDTO resultado = produtoService.adicionaProduto(produtoCreateDTO);

        // Validação
        assertEquals(produtoCreateDTO, resultado);
    }

    @Test
    public void testRetornaProdutoDTO() {
        // Cenário
        String codigo = "123456789";
        ProdutoDTO produtoDTO = new ProdutoDTO("123456789", "Produto A", new BigDecimal("10.00"), 5, new Categoria());
        when(produtoDao.retornaProdutoDTOPorCodigo(codigo)).thenReturn(produtoDTO);

        // Ação
        ProdutoDTO resultado = produtoService.retornaProdutoDTO(codigo);

        // Validação
        assertEquals(codigo, resultado.getCodigoDeBarra());
        assertEquals("Produto A", resultado.getNome());
        assertEquals(new BigDecimal("10.00"), resultado.getPreco());
        assertEquals((Integer) 5, resultado.getQuantidade());
    }

    @Test
    public void testHaProduto() {
        // Cenário
        when(produtoDao.haProduto()).thenReturn(true);

        // Ação
        boolean resultado = produtoService.haProduto();

        // Validação
        assertTrue(resultado);
    }

    @Test
    public void testGeraRelatorioProdutosPorCategoria() {
        // Cenário
        int idCategoria = 1;
        ProdutoResponseDTO produto1 = new ProdutoResponseDTO("123456789", "Produto A");
        ProdutoResponseDTO produto2 = new ProdutoResponseDTO("987654321", "Produto B");
        List<ProdutoResponseDTO> produtos = Arrays.asList(produto1, produto2);
        when(produtoDao.retornaProdutosPorCategoria(idCategoria)).thenReturn(produtos);

        // Ação
        String relatorio = produtoService.geraRelatorioProdutosPorCategoria(idCategoria);

        // Validação
        assertTrue(relatorio.contains("Produto: codigo de barra = 123456789, nome = Produto A"));
        assertTrue(relatorio.contains("Produto: codigo de barra = 987654321, nome = Produto B"));
    }

    @Test
    public void testGeraRelatorioProdutosEstoqueBaixo() {
        // Cenário
        ProdutoBaixoEstoqueResponseDTO produto1 = new ProdutoBaixoEstoqueResponseDTO("123456789", "Produto A", 2);
        ProdutoBaixoEstoqueResponseDTO produto2 = new ProdutoBaixoEstoqueResponseDTO("987654321", "Produto B", 1);
        List<ProdutoBaixoEstoqueResponseDTO> produtos = Arrays.asList(produto1, produto2);
        when(produtoDao.retornaProdutosEstoqueBaixo()).thenReturn(produtos);

        // Ação
        String relatorio = produtoService.geraRelatorioProdutosEstoqueBaixo();

        // Validação
        assertTrue(relatorio.contains("Produto = Codigo de barra: 123456789, Nome: Produto A, Quantidade: 2"));
        assertTrue(relatorio.contains("Produto = Codigo de barra: 987654321, Nome: Produto B, Quantidade: 1"));
    }

    @Test
    public void testHaProdutoComMesmoCodigoBarra() {
        // Cenário
        String codigoBarra = "123456789";
        when(produtoDao.haProdutoComMesmoCodigoBarra(codigoBarra)).thenReturn(true);

        // Ação
        boolean resultado = produtoService.haProdutoComMesmoCodigoBarra(codigoBarra);

        // Validação
        assertTrue(resultado);
    }

    @Test
    public void testAtualizaPrecoProduto() {
        // Cenário
        String codigo = "123456789";
        ProdutoAtualizarPrecoDTO atualizarPrecoDTO = new ProdutoAtualizarPrecoDTO();
        atualizarPrecoDTO.setPreco(new BigDecimal("15.00"));
        Produto produto = new Produto(1L, codigo, "Produto A", new BigDecimal("10.00"), 5, 0, new Categoria(), new HashSet<>());
        when(produtoDao.retornaProdutoPorCodigo(codigo)).thenReturn(produto);
        when(produtoDao.atualizaProduto(produto)).thenReturn(true);

        // Ação
        boolean resultado = produtoService.atualizaPrecoProduto(codigo, atualizarPrecoDTO);

        // Validação
        assertTrue(resultado);
        assertEquals(new BigDecimal("15.00"), produto.getPreco());
    }

    @Test
    public void testAtualizaQuantidadeProduto() {
        // Cenário
        String codigo = "123456789";
        ProdutoAtualizarQuantidadeDTO atualizarQuantidadeDTO = new ProdutoAtualizarQuantidadeDTO(10);
        Produto produto = new Produto(1L, codigo, "Produto A", new BigDecimal("10.00"), 5, 0, new Categoria(), new HashSet<>());
        when(produtoDao.retornaProdutoPorCodigo(codigo)).thenReturn(produto);
        when(produtoDao.atualizaProduto(produto)).thenReturn(true);

        // Ação
        boolean resultado = produtoService.atualizaQuantidadeProduto(codigo, atualizarQuantidadeDTO);

        // Validação
        assertTrue(resultado);
        assertEquals((Integer) 10, produto.getQuantidade());
    }
}
