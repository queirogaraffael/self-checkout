package gerenciador.infrastructure;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryService {

	private EntityManagerFactory entityManagerFactory;

	public EntityManagerFactory entityManagerFactory() {

		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory("persistencia");
		}
		return entityManagerFactory;
	}

	public void inicializarEntityManagerFactory() {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory("persistencia");
		}
	}

	public void fechaEntityManagerFactory() {

		if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
			entityManagerFactory.close();
		}
	}

}
