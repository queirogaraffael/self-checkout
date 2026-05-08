package gerenciador.infrastructure.exception;

public class SistemaOcupadoException extends RuntimeException {

    public SistemaOcupadoException() {
        super("Sistema ocupado. Tente novamente.");
    }
}
