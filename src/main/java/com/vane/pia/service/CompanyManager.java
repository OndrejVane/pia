package com.vane.pia.service;

import com.vane.pia.domain.Company;

public interface CompanyManager {

    void addCompany(Company company);

    Company getCompany();

    void updateCompany(Company company);
}
