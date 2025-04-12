package main.java.gerenciador.estoque.caixa.utils;

public class AutenticadorDeSenha {
	private static final String SENHA = "senha123";

	public static boolean autenticacaoSenha(String senha) {
		try {
			if (senha.equals(SENHA)) {
				return true;
			} else {
				return false;
			}
		} catch (IllegalArgumentException erro) {
			return false;
		}

	}

}
