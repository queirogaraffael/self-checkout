package com.gerenciador_estoque_fluxo_caixa.model.dao;

import com.gerenciador_estoque_fluxo_caixa.model.entities.Produto;

public interface ProdutoDao {
	void adicionaProduto(Produto produto);

	void atualizaProduto(Produto produto);

	void removeProduto(String codigo);

	Produto retornaProdutoPorCodigo(String codigo);

	String geraRelatotioProdutos(Integer categoria);

	boolean tabelaProdutoEstaVazia();

	String geraRelatorioProdutosEstoqueBaixo();

}
