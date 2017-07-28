package com.bridgeit.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.todo.dao.TokenDao;
import com.bridgeit.todo.model.Token;

@Service
@Transactional
public class TokenService {

	@Autowired
	TokenDao dao;
	
	public void saveTokenDetail(Token token) {
		dao.saveToken(token);
	}

	public void deleteToken(String deletetoken) {
		dao.deleteTokenInDB(deletetoken);
	}

	public Token getTokenfromDB(String accesstoken) {
		return dao.getTokenData(accesstoken);
	}

	public void updateAccessToken(Token generateNewAccessToken) {
		dao.updateNewAccessToken(generateNewAccessToken);
	}
	
}
