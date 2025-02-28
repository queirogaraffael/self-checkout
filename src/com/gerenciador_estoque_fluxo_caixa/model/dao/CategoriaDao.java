package com.gerenciador_estoque_fluxo_caixa.model.dao;

import com.gerenciador_estoque_fluxo_caixa.dtos.categorias.CategoriaResponseDTO;
import com.gerenciador_estoque_fluxo_caixa.model.entities.Categoria;

import java.util.List;

public interface CategoriaDao {
	List<CategoriaResponseDTO> retornaCategorias();
}
