package com.bridgeit.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgeit.todo.Utility.Redis.TokenRepository;
import com.bridgeit.todo.model.Token;

@Service
public class RedisService {

	@Autowired
	TokenRepository tokenRepositoryImpl;
	
	public void saveToken(Token token) {
		tokenRepositoryImpl.saveToken(token);
	}
}
