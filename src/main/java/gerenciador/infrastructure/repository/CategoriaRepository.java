package gerenciador.infrastructure.repository;


import gerenciador.dto.categoria.CategoriaResponseDTO;

import java.util.List;

public interface CategoriaRepository {
	List<CategoriaResponseDTO> retornaCategorias();
}
