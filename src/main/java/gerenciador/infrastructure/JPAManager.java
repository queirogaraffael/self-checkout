package gerenciador.infrastructure;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class JPAManager {

	private EntityManagerFactory entityManagerFactory;

	private Map<String, String> carregarConfiguracoesBanco() {
		Map<String, String> props = new HashMap<>();
		Properties config = new Properties();
		try (FileInputStream file = new FileInputStream("config.properties")) {
			config.load(file);
			if (config.getProperty("db.user") != null) {
				props.put("javax.persistence.jdbc.user", config.getProperty("db.user"));
			}
			if (config.getProperty("db.password") != null) {
				props.put("javax.persistence.jdbc.password", config.getProperty("db.password"));
			}
		} catch (IOException e) {
			System.err.println("ALERTA: Arquivo config.properties nao encontrado. Usando configuracoes padrao.");
		}
		return props;
	}

	public EntityManagerFactory entityManagerFactory() {

		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory("persistencia", carregarConfiguracoesBanco());
		}
		return entityManagerFactory;
	}

	public void inicializarEntityManagerFactory() {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory("persistencia", carregarConfiguracoesBanco());
		}
	}

	public void fechaEntityManagerFactory() {

		if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
			entityManagerFactory.close();
		}
	}

}
