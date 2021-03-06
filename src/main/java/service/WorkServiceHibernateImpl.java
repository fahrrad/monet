package service;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import domain.Work;

public class WorkServiceHibernateImpl implements IWorkService {

	@Override
	public Work getById(Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		Work work = null;
		try {
			transaction = session.beginTransaction();

			work = (Work) session.byId(Work.class).load(id);

		} catch (HibernateException ex) {
			transaction.rollback();
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return work;
	}

	@Override
	public List<Work> getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long insertOrUpdate(Work work) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		Long workId = null;

		try {
			transaction = session.beginTransaction();
			if (work.getId() == null) {
				workId = (Long) session.save(work);
			} else {
				session.update(work);
			}
			transaction.commit();

			System.out.println("saved work: with id" + workId);
		} catch (HibernateException ex) {
			ex.printStackTrace();
			transaction.rollback();
		} finally {
			session.close();
		}
		return workId;
	}

	@Override
	public void delete(Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			Work work = (Work) session.byId(Work.class).load(id);
			session.delete(work);

			transaction.commit();

			System.out.println("deleted work: with id" + id);
		} catch (HibernateException ex) {
			ex.printStackTrace();
			transaction.rollback();
		} finally {
			session.close();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Work> getAll() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Work> workList = null;

		try {
			session.beginTransaction();
			workList = session.createCriteria(Work.class)
					.addOrder(Order.desc("title")).list();

		} catch (HibernateException ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
			throw ex;
		} finally {
			session.close();
		}
		return workList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Work> getByQuery(String hqlQuery) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Work> workList = null;

		try {
			session.beginTransaction();
			workList = session.createQuery(hqlQuery).list();

		} catch (HibernateException ex) {
			ex.printStackTrace();
			session.getTransaction().rollback();
			throw ex;
		} finally {
			session.close();
		}
		return workList;
	}
}
