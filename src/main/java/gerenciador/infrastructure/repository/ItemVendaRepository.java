package gerenciador.infrastructure.repository;


import gerenciador.model.ItemVenda;

import java.util.Set;

public interface ItemVendaRepository {
	void adicionaItemVenda(ItemVenda itemVenda);

	Set<ItemVenda> retornaItensVenda(Integer codigoVenda);

}
