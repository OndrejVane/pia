package com.vane.pia.service;

import java.util.List;

import com.vane.pia.domain.User;
import com.vane.pia.model.WebCredentials;

public interface UserManager {

	List<User> getUsers();

	boolean addUser(User user);

	void updateCurrentUserDetails(User user);

	void changePassword(String password);

	WebCredentials getCurrentUser();

	boolean checkPassword(String password);

	void deleteUserById(Long id);

	User findUserById(Long id);

	void updateUserDetails(User user, Long id);

	void changePasswordToUser(Long userId, String newPassword);

	boolean checkUserId(Long id);

}
