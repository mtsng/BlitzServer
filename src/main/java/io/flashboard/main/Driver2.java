package io.flashboard.main;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import io.flashboard.beans.quiz.Quiz;
import io.flashboard.beans.users.User;
import io.flashboard.dao.AdminDaoImpl;
import io.flashboard.util.HibernateUtil;

public class Driver2 {
	public static void main(String[] args) { 
		//testUserCreation();
		//createAdmin();
		//approve("admin");
		//getQuiz();
	}
	
	public static void approve(String username) {
		AdminDaoImpl ad = new AdminDaoImpl();
		ad.approveUser(username);
		
	}
	
	public static void getQuiz() {
		Session session = HibernateUtil.getSession();
		Query query = null;
		String hql = "FROM Quiz";
		
		try {
			query = session.createQuery(hql);
			System.out.println(query.list());
			
		}catch(HibernateException he) {
			
		}
	}
	
	public static void createAdmin() {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		
		User newUser = new User("John", "Doe", "admin", "jDoe@flashboard.io", "password", 2);
		
		try {
			tx = session.beginTransaction();
			session.save(newUser);
			tx.commit();
			System.out.println("User: '" + newUser.getUsername() + "' has been successfully created!");
		} catch (HibernateException he) {
			if (tx != null) {
				tx.rollback();
			}
			System.out.println("User creation failed!");
			he.printStackTrace();
			
		} finally {
			session.close();
		}
	}
	
	public static void testUserCreation() {
		Session session = HibernateUtil.getSession();
		Transaction tx = null;
		
		User newUser = new User("jef", "jeff", "jeff", "jeff", "jeff");
		//SQLIntegrityConstraintViolationException
		try {
			tx = session.beginTransaction();
			session.save(newUser);
			tx.commit();
			System.out.println("User: '" + newUser.getUsername() + "' has been successfully created!");
		} catch (HibernateException he) {
			if (tx != null) {
				tx.rollback();
			}
			System.out.println("User creation failed!");
			he.printStackTrace();
			
		} finally {
			session.close();
		}
	}
}
