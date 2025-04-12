package main.java.gerenciador.estoque.caixa.model.dao;


import main.java.gerenciador.estoque.caixa.dtos.categorias.CategoriaResponseDTO;

import java.util.List;

public interface CategoriaDao {
	List<CategoriaResponseDTO> retornaCategorias();
}
