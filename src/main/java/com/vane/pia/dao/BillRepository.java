package com.vane.pia.dao;

import com.vane.pia.domain.Bill;
import org.springframework.data.repository.CrudRepository;

public interface BillRepository extends CrudRepository<Bill, Long> {

    Bill findByName(String name);

    Bill findByBillNumber(String billNumber);
}
