package gerenciador.service;

import gerenciador.model.ItemVenda;
import gerenciador.model.NotaFiscal;
import gerenciador.model.Produto;
import gerenciador.model.Venda;
import gerenciador.model.enums.ResultadoFinalizacao;
import gerenciador.model.enums.StatusNotaFiscal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.OptimisticLockException;
import javax.persistence.RollbackException;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Set;

public class FinalizarCompraService {

    private static final int MAX_TENTATIVAS = 3;
    private static final long DELAY_BASE_MS = 100;
    private static final long DELAY_MAX_MS = 1000;
    private static final Random random = new Random();

    private final EntityManagerFactory entityManagerFactory;

    public FinalizarCompraService(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public ResultadoFinalizacao finalizar(Set<ItemVenda> listaCompras, NotaFiscal notaFiscal) {
        int tentativas = 0;

        while (tentativas < MAX_TENTATIVAS) {
            EntityManager em = entityManagerFactory.createEntityManager();
            try {
                em.getTransaction().begin();

                for (ItemVenda item : listaCompras) {
                    Produto produto = em.find(Produto.class, item.getProduto().getId());

                    if (produto.getQuantidade() < item.getQuantidade()) {
                        em.getTransaction().rollback();
                        return ResultadoFinalizacao.PRODUTO_ESGOTADO.comNome(produto.getNome());
                    }

                    produto.setQuantidade(produto.getQuantidade() - item.getQuantidade());
                }

                Venda venda = new Venda();
                venda.setDataHora(LocalDateTime.now());
                em.persist(venda);

                double total = 0;
                for (ItemVenda item : listaCompras) {
                    item.setVenda(venda);
                    em.persist(item);
                    total += item.subTotal();
                }

                venda.setTotal(total);

                em.getTransaction().commit();

                if (notaFiscal.getStatusNotaFiscal() == StatusNotaFiscal.ATIVADA) {
                    GeradorNotaFiscal.geradorNotaFiscal(venda, listaCompras, notaFiscal.getCaminhoNotaFiscal());
                }

                return ResultadoFinalizacao.SUCESSO;

            } catch (RollbackException | OptimisticLockException e) {
                tentativas++;
                aguardarComBackoffEJitter(tentativas);
            } finally {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }

        return ResultadoFinalizacao.SISTEMA_OCUPADO;
    }

    private void aguardarComBackoffEJitter(int tentativa) {
        long backoff = Math.min(DELAY_MAX_MS, DELAY_BASE_MS * (1L << tentativa));
        long delay = (long) (random.nextDouble() * backoff);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
