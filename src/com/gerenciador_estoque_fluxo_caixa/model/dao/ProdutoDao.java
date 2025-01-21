package com.gerenciador_estoque_fluxo_caixa.model.dao;

import com.gerenciador_estoque_fluxo_caixa.dtos.ProdutoCreateDTO;
import com.gerenciador_estoque_fluxo_caixa.dtos.ProdutoDTO;
import com.gerenciador_estoque_fluxo_caixa.model.entities.Produto;

public interface ProdutoDao {
	ProdutoDTO adicionaProduto(ProdutoCreateDTO produtoCreateDTO);

	void atualizaProduto(Produto produto);

	void removeProduto(String codigo);

	Produto retornaProdutoPorCodigo(String codigo);

	String geraRelatotioProdutos(Integer categoria);

	boolean tabelaProdutoEstaVazia();

	String geraRelatorioProdutosEstoqueBaixo();

}
