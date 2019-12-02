package com.vane.pia.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vane.pia.domain.User;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	User findByUsername(String username);

	void deleteUserById(Long id);
}
