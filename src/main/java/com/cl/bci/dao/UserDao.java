package com.cl.bci.dao;

import com.cl.bci.model.Login;
import com.cl.bci.model.User;

public interface UserDao {

	User createuser(User user);

	User getUserById(Long id);

	void deleteUser(Long id);

	User updateUser(User user, Long id);

	boolean getExistEmail(String email);

	boolean loginUser(Login Login);

	User getUserByMail(Login Login);
	
	void updateDateLogin(String fecha, Long id);
}
