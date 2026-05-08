package gerenciador.service;

import gerenciador.dto.categoria.CategoriaResponseDTO;
import gerenciador.infrastructure.repository.CategoriaRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CategoriaServiceTest {

    private CategoriaRepository categoriaDao;
    private CategoriaService categoriaService;

    @Before
    public void setUp() {
        categoriaDao = mock(CategoriaRepository.class);
        categoriaService = new CategoriaService(categoriaDao);
    }


    @Test
    public void testRetornaCategorias() {
        // Cenário
        List<CategoriaResponseDTO> categorias = Arrays.asList(
                new CategoriaResponseDTO(1, "Padaria"),
                new CategoriaResponseDTO(2, "Bebidas")
        );

        // Ação
        when(categoriaDao.retornaCategorias()).thenReturn(categorias);
        Object[] resultado = categoriaService.retornaCategorias();

        // Validação
        assertEquals(2, resultado.length);
        assertEquals("Padaria", ((CategoriaResponseDTO) resultado[0]).getNome());
        assertEquals("Bebidas", ((CategoriaResponseDTO) resultado[1]).getNome());
    }

    @Test
    public void testConverteResultadoParaCategoriaDTO() {
        // Cenário
        Object categoriaMock = "3 - Congelados";

        // Ação
        CategoriaResponseDTO dto = categoriaService.converteResultadoParaCategoriaDTO(categoriaMock);

        // Validação
        assertEquals(Integer.valueOf(3), dto.getId());
        assertEquals("Congelados", dto.getNome());

    }


}
