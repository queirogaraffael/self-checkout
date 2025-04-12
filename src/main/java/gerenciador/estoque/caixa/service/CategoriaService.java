package main.java.gerenciador.estoque.caixa.service;



import main.java.gerenciador.estoque.caixa.dtos.categorias.CategoriaResponseDTO;
import main.java.gerenciador.estoque.caixa.model.dao.CategoriaDao;

import java.util.List;

public class CategoriaService {

    private final CategoriaDao categoriaDao;

    public CategoriaService(CategoriaDao categoriaDao) {
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
