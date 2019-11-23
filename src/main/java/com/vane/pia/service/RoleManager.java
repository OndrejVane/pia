package com.vane.pia.service;

import java.util.List;

import com.vane.pia.domain.Role;

public interface RoleManager {

	List<Role> getRoles();

	void addRole(String code, String name);

}
