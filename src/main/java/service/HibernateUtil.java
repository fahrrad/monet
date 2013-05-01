package service;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import domain.Collection;
import domain.Werk;
import domain.Work;

@SuppressWarnings("deprecation")
public class HibernateUtil {
	
	private static final SessionFactory sessionFactory;
	
	static{
		try{
			sessionFactory = new AnnotationConfiguration().configure()
					.addPackage("domain")
					.addAnnotatedClass(Work.class)
					.addAnnotatedClass(Collection.class)
					.buildSessionFactory();
		}catch(Throwable ex){
			System.err.println("Cannot create sessionFactory." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
}
