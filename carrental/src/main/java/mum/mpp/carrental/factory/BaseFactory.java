package mum.mpp.carrental.factory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

import mum.mpp.carrental.Config;
import mum.mpp.carrental.enums.UserRoleEnum;
import mum.mpp.carrental.model.Customer;
import mum.mpp.carrental.model.SystemUser;
import mum.mpp.carrental.model.User;
import mum.mpp.carrental.rulesets.RuleException;
import mum.mpp.carrental.rulesets.RuleSetFactory;

public abstract class BaseFactory {

	protected BaseFactory() {}
	
	private static SessionFactory sessionFactory = null;
	
	public static Session openSession() {
		if (sessionFactory == null) {
			final Configuration cfg = new Configuration().configure("hibernate-config.xml");
			sessionFactory = cfg.buildSessionFactory();
		}
		return sessionFactory.openSession();
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	protected static <T> List<T> searchDisjunction(Class<T> clazz, Disjunction disjunction){

		Session session = openSession();
		Criteria cr = session.createCriteria(clazz);
		
		cr.add(disjunction);
		
		List<T> list = cr.list();
		return list;
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	protected static <T> List<T> search(Class<T> clazz, List<LogicalExpression> expressions){

		Session session = openSession();
		Criteria cr = session.createCriteria(clazz);
		for(LogicalExpression exp : expressions) {
			cr.add( exp );			
		}
		List<T> list = cr.list();
		return list;
	}	
	@SuppressWarnings({ "deprecation", "unchecked" })
	protected static <T> List<T> searchByCriterions(Class<T> clazz, List<Criterion> criterions){

		Session session = openSession();
		Criteria cr = session.createCriteria(clazz);
		for(Criterion cri : criterions) {
			cr.add( cri );			
		}
		List<T> list = cr.list();
		return list;
	}	
	
	protected static <T> T findOne(Class<T> clazz, Long id) throws SQLException {

		Session session = openSession();
		T obj = session.get(clazz, id);
		closeSession(session);
        return obj;
	}
	
	protected static <T> List<T> selectAll(Class<T> clazz) throws SQLException {

		Session session = openSession();		

		CriteriaQuery<T> criteriaQuery = session.getCriteriaBuilder().createQuery(clazz);
		criteriaQuery.from(clazz);
		
		List<T> list = session.createQuery(criteriaQuery).getResultList();
		
		session.close();
		
		return list;
	}
	
	protected static <T> T save(T obj) throws RuleException {
		
		if(obj == null)
			throw new NullPointerException("Object can not be null!");

		RuleSetFactory.validateRuleSet(obj);
		
		Session session = openSession();
		session.getTransaction().begin();
					
		session.saveOrUpdate( obj ); 
	      
		session.getTransaction().commit();
		closeSession(session);
		return obj;
	}

	protected static List<Object> saveAll(List<Object> objs) throws Exception {
		
		if(objs == null)
			throw new NullPointerException("Object can not be null!");

		List<Object> returnList = new ArrayList<Object>();
		
		Session session = openSession();
		
		Transaction trans = session.getTransaction();
		
		try {
			trans.begin();

			for(Object obj : objs)
			{
				RuleSetFactory.validateRuleSet(obj);
				session.saveOrUpdate( obj ); 
				returnList.add(obj);				
			}
			
			trans.commit();
		}catch(Exception ex) {			
			trans.rollback();
			throw ex;			
		} finally {
			closeSession(session);		
		}
	      
		return returnList;
	}
	
	
	protected static <T> void delete(T object) throws RuleException {
		
		if(object == null)
			throw new NullPointerException("Object can not be null!");		
		
		Session session = openSession();
		session.getTransaction().begin();
		
		session.delete( object ); 
	      
		session.getTransaction().commit();
		closeSession(session);
	}

	public static void closeSession(Session session) {
		if (session != null) {
			session.close();
		}
	}	
	
	public static void initSessionFactory() {
		Session session = openSession();
		closeSession(session);
		System.out.println("initiated a session factory.");
	}
	
	public static void closeSessionFacetory(){
		if (sessionFactory != null) {
			sessionFactory.close();
			System.out.println("Closed the session factory.");
		}		
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static User login(String username, String password, String type) throws Exception {
		try {

			Session session = openSession();
			Criteria cr = null;

			if(type.equals("admin"))
				cr = session.createCriteria(SystemUser.class);
			else
				cr = session.createCriteria(Customer.class);

			Criterion crAdmin  = Restrictions.eq("userName", username);
			Criterion crPassword = Restrictions.eq("password", password);
			
			LogicalExpression andExp = Restrictions.and(crAdmin, crPassword);
			
			cr.add(andExp);	

			session.getTransaction().begin();
			
			List<User> list = cr.list();
			if(list.size() == 0)
				throw new Exception("Username or password is incorrect!");
			
		      
			session.getTransaction().commit();
			closeSession(session);
			
			return list.get(0);
			
		}catch(Exception ex) {
			throw ex;
		}
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static void loadInitData() {

		Session session = openSession();	

		Criteria cr = session.createCriteria(SystemUser.class);


		Criterion crAdmin  = Restrictions.eq("userName", "admin");
		cr.add(crAdmin);	

		session.getTransaction().begin();
		
		List<SystemUser> list = cr.list();
		if(list.size() == 0) {
			session.saveOrUpdate( new SystemUser("admin", "pass") ); 
		}
		else
		{
			SystemUser systemUser = list.get(0);
			systemUser.setPassword("pass");
			systemUser.setUserRole(UserRoleEnum.System);
			session.saveOrUpdate( systemUser );
		}
	      
		session.getTransaction().commit();
		closeSession(session);
	}
}
