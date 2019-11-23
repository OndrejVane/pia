package com.vane.pia.dao;

import com.vane.pia.domain.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

	Role findByCode(String code);

}
