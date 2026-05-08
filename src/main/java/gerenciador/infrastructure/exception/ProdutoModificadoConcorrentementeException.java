package gerenciador.infrastructure.exception;

public class ProdutoModificadoConcorrentementeException extends RuntimeException {

    public ProdutoModificadoConcorrentementeException(String message) {
        super(message);
    }

    public ProdutoModificadoConcorrentementeException(String message, Throwable cause) {
        super(message, cause);
    }
}
