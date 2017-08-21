package com.bridgeit.todo.dao.implementation;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgeit.todo.dao.TokenDao;
import com.bridgeit.todo.model.Token;

@Repository
public class TokenDaoImpl implements TokenDao {

	@Autowired
	SessionFactory factory;
	
	public void saveToken(Token token) {
		Session session = factory.getCurrentSession();
		session.save(token);
	}

	public void deleteTokenInDB(String deletetoken) {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("delete from Token where accesstoken =:deletetoken");
		query.setParameter("deletetoken", deletetoken);
		query.executeUpdate();
	}

	public Token getTokenData(String accesstoken) {
		Session session = factory.getCurrentSession();
		Query query = session.createQuery("from Token where accesstoken=:accesstoken");
		query.setParameter("accesstoken", accesstoken);
		Token token = (Token) query.uniqueResult();
		return token;
		
	}

	public void updateNewAccessToken(Token newAccessToken) {
		Session session = factory.getCurrentSession();
		session.update(newAccessToken);
	}

}
