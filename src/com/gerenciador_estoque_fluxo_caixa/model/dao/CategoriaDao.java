package com.gerenciador_estoque_fluxo_caixa.model.dao;

import com.gerenciador_estoque_fluxo_caixa.model.entities.Categoria;

public interface CategoriaDao {
	Object[] categorias();

	void adicionarCategoriasSeNaoTiverAinda();

	Categoria retornaCategoria();
}
