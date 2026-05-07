package gerenciador.session;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class MonitorSessao {

    private final SessaoAutoatendimento sessao;
    private final ScheduledExecutorService scheduler;
    private ScheduledFuture<?> tarefa;

    public MonitorSessao(SessaoAutoatendimento sessao) {
        this.sessao = sessao;
        this.scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "monitor-sessao");
            t.setDaemon(true);
            return t;
        });
    }

    public void iniciar(Supplier<Boolean> sacolaVazia, Runnable aoExpirar) {
        tarefa = scheduler.scheduleWithFixedDelay(() -> {
            
            if (sessao.estaExpirada()) {
                
                if (!sacolaVazia.get() || !sessao.isNoMenuAutoatendimento()) {
                    fecharDialogosAbertos();
                    
                    SwingUtilities.invokeLater(() -> {
                        aoExpirar.run();
                        sessao.reiniciar(); 
                    });
                } else {
                    sessao.reiniciar();
                }
            }
            
        }, 10, 10, TimeUnit.SECONDS);
    }

    public void parar() {
        if (tarefa != null) tarefa.cancel(false);
        scheduler.shutdown();
    }

    private void fecharDialogosAbertos() {
        for (Window window : Window.getWindows()) {
            if (window instanceof JDialog && window.isVisible()) {
                SwingUtilities.invokeLater(window::dispose);
            }
        }
    }
}
