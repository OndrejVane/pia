package com.vane.pia.dao;

import com.vane.pia.domain.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, Long> {

    Company findByName(String name);
}
