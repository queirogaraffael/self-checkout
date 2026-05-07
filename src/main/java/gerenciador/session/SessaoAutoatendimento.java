package gerenciador.session;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class SessaoAutoatendimento {

    private static final long TIMEOUT_MS = 2 * 60 * 1000L; // 2 minutos

    private final AtomicLong ultimaAtividade = new AtomicLong(System.currentTimeMillis());
    private final AtomicBoolean noMenuAutoatendimento = new AtomicBoolean(true);

    public void registrarAtividade() {
        ultimaAtividade.set(System.currentTimeMillis());
    }

    public boolean estaExpirada() {
        return System.currentTimeMillis() - ultimaAtividade.get() > TIMEOUT_MS;
    }

    public void reiniciar() {
        registrarAtividade();
    }

    public boolean isNoMenuAutoatendimento() {
        return noMenuAutoatendimento.get();
    }

    public void setNoMenuAutoatendimento(boolean status) {
        this.noMenuAutoatendimento.set(status);
    }
}
