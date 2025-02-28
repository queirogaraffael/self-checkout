package com.gerenciador_estoque_fluxo_caixa.service;

import com.gerenciador_estoque_fluxo_caixa.dtos.categorias.CategoriaResponseDTO;
import com.gerenciador_estoque_fluxo_caixa.model.dao.CategoriaDao;

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
