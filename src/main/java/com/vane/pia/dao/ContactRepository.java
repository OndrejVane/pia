package com.vane.pia.dao;

import com.vane.pia.domain.Contact;
import org.springframework.data.repository.CrudRepository;

public interface ContactRepository extends CrudRepository<Contact, Long> {

    Contact findByName(String name);
}
