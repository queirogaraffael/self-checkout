package main.java.gerenciador.estoque.caixa.model.dao;


import main.java.gerenciador.estoque.caixa.model.entities.ItemVenda;

import java.util.Set;

public interface ItemVendaDao {
	void adicionaItemVenda(ItemVenda itemVenda);

	Set<ItemVenda> retornaItensVenda(Integer codigoVenda);

}
