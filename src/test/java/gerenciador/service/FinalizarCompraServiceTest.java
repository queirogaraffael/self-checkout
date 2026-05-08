package gerenciador.service;

import gerenciador.config.NotaFiscal;
import gerenciador.infrastructure.exception.ProdutoEsgotadoException;
import gerenciador.infrastructure.exception.SistemaOcupadoException;
import gerenciador.infrastructure.repository.FinalizarCompraRepository;
import gerenciador.model.ItemVenda;
import gerenciador.model.Venda;
import gerenciador.model.enums.ResultadoFinalizacao;
import gerenciador.model.enums.StatusNotaFiscal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anySet;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FinalizarCompraServiceTest {

    @Mock
    private FinalizarCompraRepository repository;

    private FinalizarCompraService service;
    private Set<ItemVenda> listaCompras;
    private NotaFiscal notaFiscalDesativada;

    @Before
    public void setUp() {
        service = new FinalizarCompraService(repository);
        listaCompras = new HashSet<>();
        notaFiscalDesativada = new NotaFiscal();
    }

    @Test
    public void deveRetornarSucessoQuandoCompraFinalizadaComNotaFiscalDesativada() {
        when(repository.finalizar(anySet())).thenReturn(new Venda());

        ResultadoFinalizacao resultado = service.finalizar(listaCompras, notaFiscalDesativada);

        assertEquals(ResultadoFinalizacao.SUCESSO, resultado);
    }

    @Test
    public void deveRetornarSucessoQuandoCompraFinalizadaComNotaFiscalAtivada() {
        NotaFiscal notaFiscalAtivada = new NotaFiscal();
        notaFiscalAtivada.setStatusNotaFiscal(StatusNotaFiscal.ATIVADA);
        notaFiscalAtivada.setCaminhoNotaFiscal(System.getProperty("java.io.tmpdir"));

        Venda vendaComData = new Venda();
        vendaComData.setDataHora(LocalDateTime.now());
        when(repository.finalizar(anySet())).thenReturn(vendaComData);

        ResultadoFinalizacao resultado = service.finalizar(listaCompras, notaFiscalAtivada);

        assertEquals(ResultadoFinalizacao.SUCESSO, resultado);
    }

    @Test
    public void deveRetornarProdutoEsgotadoQuandoRepositoryLancaException() {
        String nomeProduto = "Arroz";
        when(repository.finalizar(anySet())).thenThrow(new ProdutoEsgotadoException(nomeProduto));

        ResultadoFinalizacao resultado = service.finalizar(listaCompras, notaFiscalDesativada);

        assertEquals(ResultadoFinalizacao.PRODUTO_ESGOTADO, resultado);
    }

    @Test
    public void devePreservarNomeDoProdutoEsgotadoNoResultado() {
        String nomeProduto = "Feijão";
        when(repository.finalizar(anySet())).thenThrow(new ProdutoEsgotadoException(nomeProduto));

        ResultadoFinalizacao resultado = service.finalizar(listaCompras, notaFiscalDesativada);

        assertEquals(nomeProduto, resultado.getNomeProduto());
    }

    @Test
    public void deveRetornarSistemaOcupadoQuandoTodasTentativasFalharem() {
        when(repository.finalizar(anySet())).thenThrow(new SistemaOcupadoException());

        ResultadoFinalizacao resultado = service.finalizar(listaCompras, notaFiscalDesativada);

        assertEquals(ResultadoFinalizacao.SISTEMA_OCUPADO, resultado);
    }
}
