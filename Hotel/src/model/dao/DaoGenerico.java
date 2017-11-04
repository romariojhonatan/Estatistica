package model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public abstract class DaoGenerico<T> {

	private EntityManager entityManager;

	protected DaoGenerico() {
		entityManager = getEntityManager();
	}

	protected EntityManager getEntityManager() {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("transacao");
		if (entityManager == null) {
			entityManager = factory.createEntityManager();
		}
		return entityManager;
	}

	public T searchForID(Class<T> clazz, int id) {
		return (T) entityManager.find(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> searchAll(Class<T> clazz) {
		Query test = entityManager.createQuery("FROM " + clazz.getName());
		return test.getResultList();
	}

	public void toPersist(T t) {
		entityManager.getTransaction().begin();
		entityManager.persist(t);
		entityManager.getTransaction().commit();
	}

	public void update(T t) {
		entityManager.getTransaction().begin();
		entityManager.merge(t);
		entityManager.getTransaction().commit();

	}

	public void remove(T t) {

		entityManager.getTransaction().begin();
		entityManager.remove(t);
		entityManager.getTransaction().commit();
	}

	public void remove(Class<T> clazz, int id) {

		T t = searchForID(clazz, id);
		remove(t);
	}

}
