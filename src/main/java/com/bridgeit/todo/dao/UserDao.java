package com.bridgeit.todo.dao;

import com.bridgeit.todo.model.User;

public interface UserDao {

	void saveUser(User user);

	void updateUser(User user);

	User loginWithTodo(String email, String password);

	User getUserbyId(String email);

	void setAccountUser(String emialToVerify);

}
