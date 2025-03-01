package com.gerenciador_estoque_fluxo_caixa.model.dao;

import com.gerenciador_estoque_fluxo_caixa.dtos.produtos.ProdutoBaixoEstoqueResponseDTO;
import com.gerenciador_estoque_fluxo_caixa.dtos.produtos.ProdutoCreateDTO;
import com.gerenciador_estoque_fluxo_caixa.dtos.produtos.ProdutoDTO;
import com.gerenciador_estoque_fluxo_caixa.dtos.produtos.ProdutoResponseDTO;
import com.gerenciador_estoque_fluxo_caixa.model.entities.Produto;

import java.util.List;

public interface ProdutoDao {
    ProdutoCreateDTO adicionaProduto(ProdutoCreateDTO produtoCreateDTO);

    void atualizaProduto(Produto produto);

    void removeProduto(String codigo);

    ProdutoDTO retornaProdutoPorCodigo(String codigo);

    List<ProdutoResponseDTO> retornaProdutosPorCategoria(Integer idCategoria);

    boolean tabelaProdutoEstaVazia();

    List<ProdutoBaixoEstoqueResponseDTO> retornaProdutosEstoqueBaixo();

    Boolean haProdutoComMesmoCodigoBarra(String codigo);

}
