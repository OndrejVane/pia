package com.vane.pia.service;

import java.util.List;

import com.vane.pia.domain.Role;
import com.vane.pia.domain.User;
import com.vane.pia.model.Roles;

public interface RoleManager {

	List<Role> getRoles();

	void addRole(Roles roles);

	List<Role> getAllRolesUserDontHave(User user);

	List<Role> getRolesByIds(Long[] ids);

}
