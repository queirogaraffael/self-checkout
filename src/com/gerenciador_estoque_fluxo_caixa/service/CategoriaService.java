package com.gerenciador_estoque_fluxo_caixa.service;

import com.gerenciador_estoque_fluxo_caixa.model.dao.CategoriaDao;

public class CategoriaService {

    private final CategoriaDao categoriaDao;

    public CategoriaService(CategoriaDao categoriaDao) {
        this.categoriaDao = categoriaDao;
    }

    public int retornaIdCategoria() {
        return 0;
    }

    public Object categorias() {
        return null;
    }
}
