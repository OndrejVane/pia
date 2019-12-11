package com.vane.pia.service;

import com.vane.pia.domain.Bill;

import java.util.List;

public interface BillManager {

    List<Bill> getBills();

    void addBill(Bill bill);

    Bill getBillById(Long id);

    List<Bill> getAllIssuedBills();

    List<Bill> getAllAcceptedBills();

    void deleteBillById(Long id);
}
