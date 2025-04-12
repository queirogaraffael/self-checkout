package main.java.gerenciador.estoque.caixa.utils;

import java.io.File;
import java.io.IOException;

public class VerificaDiretorio {

	public static boolean verificarDiretorio(String caminho) {

		File diretorio = new File(caminho);

		if (!diretorio.exists() || !diretorio.isDirectory()) {
			return false;
		}

		try {
			File testeArquivo = new File(diretorio, "teste.txt");
			if (!testeArquivo.createNewFile()) {
				return false;
			} else {
				testeArquivo.delete();
			}
		} catch (IOException e) {
			return false;
		}

		return true;
	}
}
