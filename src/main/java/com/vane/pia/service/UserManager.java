package com.vane.pia.service;

import java.util.List;

import com.vane.pia.domain.User;

public interface UserManager {

	List<User> getUsers();

	void addUser(String username, String password);

}
