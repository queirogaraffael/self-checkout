package gerenciador.service;

import gerenciador.config.NotaFiscal;
import gerenciador.model.enums.StatusNotaFiscal;
import gerenciador.util.VerificaDiretorio;

public class NotaFiscalService {

    public boolean configurarCaminho(NotaFiscal notaFiscal, String path) {
        if (VerificaDiretorio.verificarDiretorio(path)) {
            notaFiscal.setCaminhoNotaFiscal(path);
            notaFiscal.setStatusNotaFiscal(StatusNotaFiscal.ATIVADA);
            return true;
        }
        return false;
    }
}
