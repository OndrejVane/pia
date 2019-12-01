package com.vane.pia.service;

import java.util.List;

import com.vane.pia.domain.User;

public interface UserManager {

	List<User> getUsers();

	boolean addUser(User user);

}
