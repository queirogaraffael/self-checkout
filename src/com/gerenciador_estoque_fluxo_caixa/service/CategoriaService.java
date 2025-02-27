package com.gerenciador_estoque_fluxo_caixa.service;

import com.gerenciador_estoque_fluxo_caixa.model.dao.CategoriaDao;
import com.gerenciador_estoque_fluxo_caixa.model.entities.Categoria;

public class CategoriaService {

    private final CategoriaDao categoriaDao;

    public CategoriaService(CategoriaDao categoriaDao) {
        this.categoriaDao = categoriaDao;
    }

    public Categoria retornaIdCategoria() {
        return categoriaDao.retornaCategoria();

    }

    public Object categorias() {
        return null;
    }
}
