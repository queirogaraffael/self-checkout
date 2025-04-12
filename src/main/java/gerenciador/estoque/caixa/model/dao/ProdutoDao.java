package main.java.gerenciador.estoque.caixa.model.dao;

import main.java.gerenciador.estoque.caixa.dtos.produtos.*;
import main.java.gerenciador.estoque.caixa.model.entities.Produto;

import java.util.List;

public interface ProdutoDao {
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
