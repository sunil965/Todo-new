package com.bridgeit.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgeit.todo.dao.UserDao;
import com.bridgeit.todo.model.User;

@Service
@Transactional
public class UserServices {

	@Autowired
	UserDao dao;
	
	public void saveUserDetails(User user) {
		dao.saveUser(user);
	}

	public void updateUserDetails(User user) {
		dao.updateUser(user);
	}

	public List<User> getUserById(int id) {
		return dao.getUserbyId(id);
	}
	
	public User loginWithTodo(String email, String password) {
		return dao.loginWithTodo(email, password);
	}

}
