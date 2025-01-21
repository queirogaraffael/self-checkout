package com.gerenciador_estoque_fluxo_caixa.model.dao;

public interface CategoriaDao {
	Object[] categorias();

	void adicionarCategoriasSeNaoTiverAinda();

	Integer retornaIdCategoria();
}
