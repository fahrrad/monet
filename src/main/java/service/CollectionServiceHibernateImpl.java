package service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import domain.Collection;
import domain.Work;

public class CollectionServiceHibernateImpl implements ICollectionService {

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
			if (collection.getId() == null || collection.getId() == 0) {
				Collection existingCollection = this.getByName(collection.getName());
				if(existingCollection == null){
					id = (Long) session.save(collection);					
				}else{
					throw new HibernateException("Collectie " + collection.getName() + " bestaat al" );
				}
				
			} else {
				session.update(collection);
			}
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
			throw ex;
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
			@SuppressWarnings("unchecked")
			List<Collection> list = crit.list();
			if(!list.isEmpty()){
				collection = (Collection) list.get(0); 
			}
		} catch (HibernateException ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
		return collection;
	}

	@Override
	public void delete(Long id) throws CouldNotDeleteException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			session.beginTransaction();
			
			Collection collection = (Collection) session.byId(Collection.class)
					.load(id);
			session.delete(collection);

			session.getTransaction().commit();
		} catch (HibernateException ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
			throw new CouldNotDeleteException();
		} finally {
			session.close();
		}
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
	
	
	@Override
	public List<String> getAllNames() {		
		List<String> collectionNames = new ArrayList<String>();
		for(Collection c : getAll()){
			collectionNames.add(c.getName());
		}
		return collectionNames;
	}
}
