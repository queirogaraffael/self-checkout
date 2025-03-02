package com.gerenciador_estoque_fluxo_caixa.model.dao;

import java.time.LocalDate;
import java.util.List;

import com.gerenciador_estoque_fluxo_caixa.dtos.vendas.VendaResponseDTO;
import com.gerenciador_estoque_fluxo_caixa.model.entities.Venda;

public interface VendaDao {
	void adicionaVenda(Venda venda);

	VendaResponseDTO retornaVendaDTOPorCodigo(Integer codigo);

	List<VendaResponseDTO> retornaVendas();

	List<VendaResponseDTO> retornaVendasPorData(LocalDate data);

	boolean haVenda();

	boolean haVendaComEsseCodigo(Integer codigo);
}
