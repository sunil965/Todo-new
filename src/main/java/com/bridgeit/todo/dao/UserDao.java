package com.bridgeit.todo.dao;

import java.util.List;

import com.bridgeit.todo.model.User;

public interface UserDao {

	void saveUser(User user);

	void updateUser(User user);

	User loginWithTodo(String email, String password);

	List<User> getUserbyId(int id);

}
