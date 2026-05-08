package gerenciador.infrastructure.repository;

import gerenciador.model.ItemVenda;
import gerenciador.model.Venda;
import gerenciador.infrastructure.exception.ProdutoEsgotadoException;
import gerenciador.infrastructure.exception.SistemaOcupadoException;

import java.util.Set;

public interface FinalizarCompraRepository {

    /**
     * Persiste a venda e seus itens de forma transacional com retry por
     * concorrência.
     *
     * @return a Venda salva com ID gerado
     * @throws ProdutoEsgotadoException se algum produto não tiver estoque
     *                                  suficiente
     * @throws SistemaOcupadoException  se todas as tentativas falharem por conflito
     */
    Venda finalizar(Set<ItemVenda> listaCompras);
}
