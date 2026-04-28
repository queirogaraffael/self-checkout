package gerenciador.infrastructure.repository;

import gerenciador.dto.produto.*;
import gerenciador.model.Produto;

import java.util.List;

public interface ProdutoRepository {
    ProdutoCreateDTO adicionaProduto(ProdutoCreateDTO produtoCreateDTO);

    boolean atualizaProduto(Produto produto);

    ProdutoDTO retornaProdutoDTOPorCodigo(String codigo);

    ProdutoAtualizarQuantidadeDTO retornaProdutoAtualizarQuantidadeDTO(String codigo);

    Produto retornaProdutoPorCodigo(String codigo);

    List<ProdutoResponseDTO> retornaProdutosPorCategoria(Integer idCategoria);

    boolean haProduto();

    List<ProdutoBaixoEstoqueResponseDTO> retornaProdutosEstoqueBaixo();

    Boolean haProdutoComMesmoCodigoBarra(String codigo);

}
