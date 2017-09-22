package com.bridgeit.todo.Utility.Redis;

import com.bridgeit.todo.model.Token;

public interface TokenRepository {

	void saveToken(Token token);
	
	Token findToken(String accessToken);
	
	void deleteToken(int id);
}
