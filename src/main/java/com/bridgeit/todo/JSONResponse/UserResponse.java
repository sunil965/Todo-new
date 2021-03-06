package com.bridgeit.todo.JSONResponse;

import org.springframework.stereotype.Component;

import com.bridgeit.todo.model.Token;
import com.bridgeit.todo.model.User;

@Component
public class UserResponse extends Response {
	Token token;

	User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

}
