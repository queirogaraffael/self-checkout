package com.gerenciador_estoque_fluxo_caixa.service;

import com.gerenciador_estoque_fluxo_caixa.dtos.vendas.VendaDTO;
import com.gerenciador_estoque_fluxo_caixa.dtos.vendas.VendaResponseDTO;
import com.gerenciador_estoque_fluxo_caixa.model.dao.VendaDao;
import com.gerenciador_estoque_fluxo_caixa.model.entities.Venda;

import java.time.LocalDate;
import java.util.List;

public class VendaService {

    private final VendaDao vendaDao;

    public VendaService(VendaDao vendaDao) {
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
        Double total = 0.0;

        StringBuilder sb = new StringBuilder();

        for (VendaResponseDTO venda : vendas) {
            sb.append(venda + "\n");
            total += venda.getTotal();
        }

        sb.append("Total: ").append(total);

        return sb.toString();
    }

    public String gerarResumoVenda(VendaResponseDTO venda, String itensVenda) {
        return venda.toStringSemPreco() + "\n"
                + itensVenda
                + String.format("Total: %.2f", venda.getTotal());
    }

}
