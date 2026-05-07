package gerenciador.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AutenticadorDeSenha {

	private static final String CONFIG_FILE = "config.properties";

	private static String obterSenhaDoArquivo() {
		Properties propriedades = new Properties();
		try (FileInputStream file = new FileInputStream(CONFIG_FILE)) {
			propriedades.load(file);
			return propriedades.getProperty("senha.admin");
		} catch (IOException e) {
			return null;
		}
	}

	public static boolean autenticacaoSenha(String senhaDigitada) {
		if (senhaDigitada == null || senhaDigitada.trim().isEmpty()) {
			return false;
		}

		String senhaCorreta = obterSenhaDoArquivo();

		if (senhaCorreta == null || senhaCorreta.trim().isEmpty()) {
			System.err.println("ALERTA DE SEGURANÇA: Senha não configurada no arquivo " + CONFIG_FILE);
			return false;
		}

		return senhaDigitada.equals(senhaCorreta);
	}

}
