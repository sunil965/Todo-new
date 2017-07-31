package com.bridgeit.todo.JSONResponse;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bridgeit.todo.model.Note;
import com.bridgeit.todo.model.Token;

@Component
public class UserResponse extends Response
{
	List<Note> list;
	Token token;
	
	public List<Note> getList() {
		return list;
	}
	public void setList(List<Note> list) {
		this.list = list;
	}
	public Token getToken() {
		return token;
	}
	public void setToken(Token token) {
		this.token = token;
	}
	
}
