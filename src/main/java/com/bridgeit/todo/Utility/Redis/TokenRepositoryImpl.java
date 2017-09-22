package com.bridgeit.todo.Utility.Redis;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import com.bridgeit.todo.model.Token;

@Repository
public class TokenRepositoryImpl implements TokenRepository {


	private static final String KEY = "Token";

	private RedisTemplate<String, Token> redisTemplate;
	private HashOperations hashOps;

	@Autowired
	private TokenRepositoryImpl(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void init() {
		hashOps = redisTemplate.opsForHash();
	}

	@SuppressWarnings("unchecked")
	public void saveToken(Token token) {
		hashOps.put(KEY, token.getAccesstoken(), token);
	}

	@SuppressWarnings("unchecked")
	public Token findToken(String accessToken) {
		return (Token) hashOps.get(KEY, accessToken);
	}

	@SuppressWarnings("unchecked")
	public void deleteToken(int id) {
		hashOps.delete(KEY, id);
	}
}
