package service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import domain.Collection;

public class CollectionServiceHibernateImpl implements ICollecionService {

	@Override
	public Collection getById(Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Collection collection = null;

		try {
			session.beginTransaction();
			collection = (Collection) session.byId(Collection.class).load(id);
		} catch (HibernateException ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return collection;
	}

	@Override
	public Long insertOrUpdate(Collection collection) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		Long id = null;
		try {
			session.beginTransaction();
			id = (Long) session.save(collection);
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return id;
	}

	@Override
	public Collection getByName(String name) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Collection collection = null;

		Criteria crit = session.createCriteria(Collection.class).add(
				Restrictions.like("name", "%" + name + "%"));

		try {
			session.beginTransaction();
			// First item from the list
			collection = (Collection) crit.list().get(0);
		} catch (HibernateException ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return collection;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Collection> getAll() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Collection> collections = null;

		try {
			session.beginTransaction();

			collections = session.createCriteria(Collection.class).list();

		} catch (HibernateException ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return collections;
	}
}
