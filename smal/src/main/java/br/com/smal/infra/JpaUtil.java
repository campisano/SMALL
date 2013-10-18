package br.com.smal.infra;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {

	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("mobicare");
	
	public EntityManager getEntityManager(){
		
		return entityManagerFactory.createEntityManager();
	}
	
}