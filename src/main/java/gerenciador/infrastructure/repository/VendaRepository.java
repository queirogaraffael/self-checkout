package gerenciador.infrastructure.repository;


import gerenciador.dto.venda.VendaResponseDTO;
import gerenciador.model.Venda;

import java.time.LocalDate;
import java.util.List;

public interface VendaRepository {
	void adicionaVenda(Venda venda);

	void atualizarVenda(Venda venda);

	VendaResponseDTO retornaVendaDTOPorCodigo(Integer codigo);

	List<VendaResponseDTO> retornaVendas();

	List<VendaResponseDTO> retornaVendasPorData(LocalDate data);

	boolean haVenda();

	boolean haVendaComEsseCodigo(Integer codigo);
}
