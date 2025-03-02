package com.gerenciador_estoque_fluxo_caixa.model.dao;

import com.gerenciador_estoque_fluxo_caixa.dtos.produtos.ProdutoBaixoEstoqueResponseDTO;
import com.gerenciador_estoque_fluxo_caixa.dtos.produtos.ProdutoCreateDTO;
import com.gerenciador_estoque_fluxo_caixa.dtos.produtos.ProdutoDTO;
import com.gerenciador_estoque_fluxo_caixa.dtos.produtos.ProdutoResponseDTO;
import com.gerenciador_estoque_fluxo_caixa.model.entities.Produto;

import java.util.List;

public interface ProdutoDao {
    ProdutoCreateDTO adicionaProduto(ProdutoCreateDTO produtoCreateDTO);

    boolean atualizaProduto(Produto produto);

    ProdutoDTO retornaProdutoDTOPorCodigo(String codigo);

    Produto retornaProdutoPorCodigo(String codigo);

    List<ProdutoResponseDTO> retornaProdutosPorCategoria(Integer idCategoria);

    boolean haProduto();

    List<ProdutoBaixoEstoqueResponseDTO> retornaProdutosEstoqueBaixo();

    Boolean haProdutoComMesmoCodigoBarra(String codigo);

}
