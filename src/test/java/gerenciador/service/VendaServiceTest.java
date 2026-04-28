package gerenciador.service;

import gerenciador.dto.venda.VendaResponseDTO;
import gerenciador.infrastructure.repository.VendaRepository;
import gerenciador.model.ItemVenda;
import gerenciador.model.Venda;
import gerenciador.service.VendaService;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class VendaServiceTest {

    private VendaRepository vendaDao;
    private VendaService vendaService;

    @Before
    public void setUp() {
        vendaDao = mock(VendaRepository.class);
        vendaService = new VendaService(vendaDao);
    }

    @Test
    public void testAdicionaVenda() {
        // Cenário
        Set<ItemVenda> itensVenda = new HashSet<>();
        LocalDateTime dataHora = LocalDateTime.now();
        Venda venda = new Venda(null, dataHora, 10.0, itensVenda);

        // Ação
        vendaService.adicionaVenda(venda);

        // Validação
        verify(vendaDao, times(1)).adicionaVenda(venda);
    }

    @Test
    public void testAtualizaVenda() {
        // Cenário
        Set<ItemVenda> itensVenda = new HashSet<>();
        LocalDateTime dataHora = LocalDateTime.now();
        Venda venda = new Venda(null, dataHora, 10.0, itensVenda);

        // Ação
        vendaService.atualizaVenda(venda);

        // Validação
        verify(vendaDao, times(1)).atualizarVenda(venda);
    }

    @Test
    public void testHaVenda() {
        // Cenário
        when(vendaDao.haVenda()).thenReturn(true);

        // Ação
        boolean resultado = vendaService.haVenda();

        // Validação
        assertTrue(resultado);
    }

    @Test
    public void testHaVendaComEsseCodigo() {
        // Cenário
        Integer codigo = 1;
        when(vendaDao.haVendaComEsseCodigo(codigo)).thenReturn(true);

        // Ação
        boolean resultado = vendaService.haVendaComEsseCodigo(codigo);

        // Validação
        assertTrue(resultado);
    }

    @Test
    public void testRetornaVenda() {
        // Cenário
        Integer codigo = 1;
        LocalDateTime dataHora = LocalDateTime.of(2023, Month.APRIL, 13, 10, 30);
        Double total = 10.0;
        VendaResponseDTO vendaDTO = new VendaResponseDTO(codigo, dataHora, total);

        when(vendaDao.retornaVendaDTOPorCodigo(codigo)).thenReturn(vendaDTO);

        // Ação
        VendaResponseDTO resultado = vendaService.retornaVenda(codigo);

        // Validação
        assertEquals(codigo, resultado.getCodigo());
        assertEquals(dataHora, resultado.getDataHora());
        assertEquals(total, resultado.getTotal(), 0.01);
    }

    @Test
    public void testRetornaRelatorioVendas() {
        // Cenário
        List<VendaResponseDTO> vendas = Arrays.asList(
                new VendaResponseDTO(1, LocalDateTime.of(2023, 4, 13, 10, 30), 10.0),
                new VendaResponseDTO(2, LocalDateTime.of(2023, 4, 14, 11, 45), 20.0)
        );
        when(vendaDao.retornaVendas()).thenReturn(vendas);

        // Ação
        String relatorio = vendaService.retornaRelatorioVendas();

        // Validação
        assertTrue(relatorio.contains("Venda: Codigo = 1"));
        assertTrue(relatorio.contains("Venda: Codigo = 2"));
        assertTrue(relatorio.contains("Total:"));
    }

    @Test
    public void testRetornaRelatorioVendasPorData() {
        // Cenário
        LocalDate data = LocalDate.of(2025, 4, 1);
        List<VendaResponseDTO> vendas = Arrays.asList(
                new VendaResponseDTO(1, LocalDateTime.of(2025, 4, 1, 10, 30), 10.0),
                new VendaResponseDTO(2, LocalDateTime.of(2025, 4, 1, 15, 45), 20.0)
        );
        when(vendaDao.retornaVendasPorData(data)).thenReturn(vendas);

        // Ação
        String relatorio = vendaService.retornaRelatorioVendasPorData(data);

        // Validação
        assertTrue(relatorio.contains("Venda: Codigo = 1"));
        assertTrue(relatorio.contains("Venda: Codigo = 2"));
        assertTrue(relatorio.contains("Total:"));
    }


    @Test
    public void testGerarResumoVenda() {
        // Cenário
        VendaResponseDTO vendaDTO = new VendaResponseDTO(1, LocalDateTime.of(2025, 4, 1, 10, 30), 10.0);
        String itensVenda = "Item 1: Produto A, Quantidade: 2";

        // Ação
        String resumo = vendaService.gerarResumoVenda(vendaDTO, itensVenda);

        // Validação
        assertTrue(resumo.contains("Produto A"));
        assertTrue(resumo.contains("Total: 10.00"));
        assertTrue(resumo.contains("Item 1: Produto A"));
    }
}
