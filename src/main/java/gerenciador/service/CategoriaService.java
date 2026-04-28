package gerenciador.service;



import gerenciador.dto.categoria.CategoriaResponseDTO;
import gerenciador.infrastructure.repository.CategoriaRepository;

import java.util.List;

public class CategoriaService {

    private final CategoriaRepository categoriaDao;

    public CategoriaService(CategoriaRepository categoriaDao) {
        this.categoriaDao = categoriaDao;
    }

    public Object[] retornaCategorias() {
        return transformaListaEmObject(categoriaDao.retornaCategorias());
    }

    public CategoriaResponseDTO converteResultadoParaCategoriaDTO(Object resultadoCategoria) {
        String[] categoriaDado = resultadoCategoria.toString().split(" - ");
        return new CategoriaResponseDTO(Integer.parseInt(categoriaDado[0]), categoriaDado[1]);
    }

    private Object[] transformaListaEmObject(List<?> lista) {
        return lista.toArray();
    }


}
