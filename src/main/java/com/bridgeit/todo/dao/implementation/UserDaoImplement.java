package com.bridgeit.todo.dao.implementation;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeit.todo.dao.UserDao;
import com.bridgeit.todo.model.User;

@Repository
public class UserDaoImplement implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;
 
	public void saveUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.save(user);
	}

	public void updateUser(User user) {
		Session session = sessionFactory.getCurrentSession();
			session.update(user);
	}

	public User getUserbyId(String email) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("email", email));
		
		User user=(User) criteria.uniqueResult();
		return user;
	}

	public User loginWithTodo(String email, String password) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.conjunction())
				.add(Restrictions.eq("email", email))
				.add(Restrictions.eq("password", password))
				.add(Restrictions.eq("isActive", true));
		
		User userresult=(User) criteria.uniqueResult();
		
		return userresult;
	}

	public void setAccountUser(String emialToVerify) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("update User set isActive = true where email=:emailid");
		query.setParameter("emailid", emialToVerify);
		query.executeUpdate();
		System.out.println("Account Status Updated");
	}

	public void updatePassword(String updateonemail, String newpassword) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("update User set password=:new where email=:emailid");
		query.setParameter("new", newpassword);
		query.setParameter("emailid", updateonemail);
		query.executeUpdate();
		System.out.println("Password Updated");
	}

	public void updateDpImage(String emailid, String dpimage) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("update User set profileImage=:image where email=:emailid");
		query.setParameter("image", dpimage);
		query.setParameter("emailid", emailid);
		query.executeUpdate();
		System.out.println("Image Updated");
	}
}
