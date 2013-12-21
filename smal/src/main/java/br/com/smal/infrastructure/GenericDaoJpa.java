package br.com.smal.infrastructure;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
//import javax.persistence.PersistenceContext;
import javax.persistence.Query;

//import org.springframework.stereotype.Repository;
/*
 @Repository
 public class GenericDaoJpa<T> {

 Logger logger = Logger.getLogger(GenericDaoJpa.class.getName());

 protected EntityManager entityManager;

 public EntityManager getEntityManager() {
 return entityManager;
 }

 @PersistenceContext
 public void setEntityManager(EntityManager entityManager) {
 this.entityManager = entityManager;
 }
 */

public class GenericDaoJpa<T> {

	private static EntityManager entityManager;

	Logger logger = Logger.getLogger(GenericDaoJpa.class.getName());

	public GenericDaoJpa() {
		GenericDaoJpa.entityManager = new JpaUtil().getEntityManager();

	}

	public boolean incluir(T entidade) {
		EntityTransaction tx = null;
		try {
			tx = entityManager.getTransaction();
			tx.begin();
			entityManager.persist(entidade);
			tx.commit();
		} catch (Exception ex) {
			if (tx != null && tx.isActive())
				tx.rollback();
			throw new DAOException("Erro na inclusão de objeto.", ex);
		}
		return true;
	}

	public boolean excluir(Class<T> c, Object id) {
		EntityTransaction tx = null;
		try {
			tx = entityManager.getTransaction();
			tx.begin();
			T entidade = entityManager.find(c, id);
			entityManager.remove(entidade);
			tx.commit();
		} catch (Exception ex) {
			if (tx != null && tx.isActive())
				tx.rollback();
			throw new DAOException("Erro durante a exclusão.", ex);
		}
		return true;
	}

	public boolean alterar(T entidade) {
		EntityTransaction tx = null;
		try {
			tx = entityManager.getTransaction();
			tx.begin();
			entidade = entityManager.merge(entidade);
			tx.commit();
		} catch (Exception ex) {
			if (tx != null && tx.isActive())
				tx.rollback();
			throw new DAOException("Erro durante a inclusão.", ex);
		}
		return true;
	}

	public T obterPorId(Class<T> c, Object id) throws DAOException {
		T entidade = null;

		try {
			logger.info("Resgatando entidade cujo id é " + id + " da classe: "
					+ c.getName());
			entidade = entityManager.find(c, id);
		} catch (RuntimeException ex) {
			throw new DAOException("Erro durante a pesquisa de objeto.", ex);
		}
		return entidade;
	}

	public List<T> obterTodos(Class<T> c) {
		logger.info("Resgatando todos as entidades da classe: " + c.getName());

		String entityName;

		entityName = c.getName().substring(c.getName().lastIndexOf('.') + 1);

		return obterEntidades("SELECT e FROM " + entityName + " e");
	}

	public List<T> obterEntidades(String queryString,
			final Object... positionalParams) {
		Query query = entityManager.createQuery(queryString);
		int i = 0;
		for (Object p : positionalParams) {
			query.setParameter(++i, p);
		}
		@SuppressWarnings("unchecked")
		List<T> l = query.getResultList();

		return l;
	}

	public T obterEntidade(String queryString, final Object... positionalParams) {
		Query query = entityManager.createQuery(queryString);
		int i = 0;
		for (Object p : positionalParams) {
			query.setParameter(++i, p);
		}
		@SuppressWarnings("unchecked")
		T entidade = (T) query.getSingleResult();

		return entidade;
	}
}
