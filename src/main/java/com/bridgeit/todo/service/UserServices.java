package com.bridgeit.todo.service;

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

	public User getUserById(String email) {
		return dao.getUserbyId(email);
	}
	
	public User loginWithTodo(String email, String password) {
		return dao.loginWithTodo(email, password);
	}

	public void activateUser(String emialToVerify) {
		dao.setAccountUser(emialToVerify);
	}

	public void saveUpdatePassword(String updateonemail, String newpassword) {
		dao.updatePassword(updateonemail, newpassword);
	}

}
