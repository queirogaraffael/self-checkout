package com.gerenciador_estoque_fluxo_caixa.model.dao;

import java.time.LocalDate;

import com.gerenciador_estoque_fluxo_caixa.model.entities.Venda;

public interface VendaDao {
	void adicionaVenda(Venda vendaInterface);

	void atualizaVenda(Venda vendaInterface);

	Venda retornaVendaPorCodigo(Integer codigo);

	String geraRelatioVendas();

	boolean tabelaVendaEstaVazia();

	String geraRelatiorioVendasPorData(LocalDate data);

}
