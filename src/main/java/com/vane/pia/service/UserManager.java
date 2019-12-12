package com.vane.pia.service;

import java.util.List;

import com.vane.pia.domain.Role;
import com.vane.pia.domain.User;
import com.vane.pia.exception.LastAdminDeletingException;
import com.vane.pia.model.WebCredentials;

public interface UserManager {

	List<User> getUsers();

	boolean addUser(User user);

	WebCredentials getCurrentUser();

	boolean checkPassword(String password);

	void deleteUserById(Long id) throws LastAdminDeletingException;

	User findUserById(Long id);

	void updateUserDetails(User user, Long id, List<Role> roles) throws LastAdminDeletingException;

	void changePasswordToUser(Long userId, String newPassword);

	boolean checkUserId(Long id);

}
