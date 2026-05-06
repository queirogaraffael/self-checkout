package gerenciador.service;



import gerenciador.dto.venda.VendaResponseDTO;
import gerenciador.infrastructure.repository.VendaRepository;
import gerenciador.model.Venda;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class VendaService {

    private final VendaRepository vendaDao;

    public VendaService(VendaRepository vendaDao) {
        this.vendaDao = vendaDao;
    }

    public void adicionaVenda(Venda venda){
        vendaDao.adicionaVenda(venda);
    }

    public void atualizaVenda(Venda venda){
        vendaDao.atualizarVenda(venda);
    }

    public boolean haVenda() {
        return vendaDao.haVenda();
    }

    public boolean haVendaComEsseCodigo(Integer codigo){
        return vendaDao.haVendaComEsseCodigo(codigo);
    }

    public VendaResponseDTO retornaVenda(Integer codigo){
        return vendaDao.retornaVendaDTOPorCodigo(codigo);
    }

    public String retornaRelatorioVendas() {
        List<VendaResponseDTO> vendas = vendaDao.retornaVendas();
        return geraRelatorio(vendas);
    }

    public String retornaRelatorioVendasPorData(LocalDate data) {
        List<VendaResponseDTO> vendas = vendaDao.retornaVendasPorData(data);
        return geraRelatorioVendaComPrecoTotal(vendas);
    }

    private String geraRelatorio(List<?> vendas){
        StringBuilder sb = new StringBuilder();

        for (Object venda : vendas) {
            sb.append(venda).append("\n");
        }
        return sb.toString();
    }

    private String geraRelatorioVendaComPrecoTotal(List<VendaResponseDTO> vendas){
        BigDecimal total = BigDecimal.ZERO;

        StringBuilder sb = new StringBuilder();

        for (VendaResponseDTO venda : vendas) {
            sb.append(venda).append("\n");
            total = total.add(venda.getTotal());
        }

        sb.append("Total: ").append(String.format("%.2f", total));

        return sb.toString();
    }

    public String gerarResumoVenda(VendaResponseDTO venda, String itensVenda) {
        return venda.toStringSemPreco() + "\n"
                + itensVenda
                + String.format("Total: %.2f", venda.getTotal());
    }

}
