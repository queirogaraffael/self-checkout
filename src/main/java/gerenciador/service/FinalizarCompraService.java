package gerenciador.service;

import gerenciador.config.NotaFiscal;
import gerenciador.infrastructure.exception.ProdutoEsgotadoException;
import gerenciador.infrastructure.exception.SistemaOcupadoException;
import gerenciador.infrastructure.repository.FinalizarCompraRepository;
import gerenciador.model.ItemVenda;
import gerenciador.model.Venda;
import gerenciador.model.enums.ResultadoFinalizacao;
import gerenciador.model.enums.StatusNotaFiscal;

import java.util.Set;

public class FinalizarCompraService {

    private final FinalizarCompraRepository finalizarCompraRepository;

    public FinalizarCompraService(FinalizarCompraRepository finalizarCompraRepository) {
        this.finalizarCompraRepository = finalizarCompraRepository;
    }

    public ResultadoFinalizacao finalizar(Set<ItemVenda> listaCompras, NotaFiscal notaFiscal) {
        try {
            Venda venda = finalizarCompraRepository.finalizar(listaCompras);

            if (notaFiscal.getStatusNotaFiscal() == StatusNotaFiscal.ATIVADA) {
                GeradorNotaFiscal.geradorNotaFiscal(venda, listaCompras, notaFiscal.getCaminhoNotaFiscal());
            }

            return ResultadoFinalizacao.SUCESSO;

        } catch (ProdutoEsgotadoException e) {
            return ResultadoFinalizacao.PRODUTO_ESGOTADO.comNome(e.getNomeProduto());

        } catch (SistemaOcupadoException e) {
            return ResultadoFinalizacao.SISTEMA_OCUPADO;
        }
    }
}
