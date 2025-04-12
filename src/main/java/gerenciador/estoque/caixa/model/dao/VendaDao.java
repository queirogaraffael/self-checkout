package main.java.gerenciador.estoque.caixa.model.dao;


import main.java.gerenciador.estoque.caixa.dtos.vendas.VendaResponseDTO;
import main.java.gerenciador.estoque.caixa.model.entities.Venda;

import java.time.LocalDate;
import java.util.List;

public interface VendaDao {
	void adicionaVenda(Venda venda);

	void atualizarVenda(Venda venda);

	VendaResponseDTO retornaVendaDTOPorCodigo(Integer codigo);

	List<VendaResponseDTO> retornaVendas();

	List<VendaResponseDTO> retornaVendasPorData(LocalDate data);

	boolean haVenda();

	boolean haVendaComEsseCodigo(Integer codigo);
}
