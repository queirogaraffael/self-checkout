package com.gerenciador_estoque_fluxo_caixa.model.dao;

import java.util.Set;

import com.gerenciador_estoque_fluxo_caixa.model.entities.ItemVenda;
import com.gerenciador_estoque_fluxo_caixa.model.entities.Venda;

public interface ItemVendaDao {
	void adicionaItemVenda(ItemVenda itemVenda);

	Set<ItemVenda> retornaItensVenda(Venda venda);

}
