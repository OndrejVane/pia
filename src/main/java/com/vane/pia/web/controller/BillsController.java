package com.vane.pia.web.controller;

import com.vane.pia.domain.Bill;
import com.vane.pia.service.BillManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
public class BillsController {

    @Autowired
    private BillManager billManager;

    @GetMapping("/bills")
    public String index(Model model) {
        List<Bill> bills = billManager.getBills();
        model.addAttribute("bills", bills);
        return "bills";
    }


    @GetMapping("/bills-issued")
    public String showAllIssuedBills(Model model) {
        List<Bill> bills = billManager.getAllIssuedBills();
        model.addAttribute("bills", bills);
        return "bills";
    }

    @GetMapping("/bills-accepted")
    public String showAllAcceptedBills(Model model) {
        List<Bill> bills = billManager.getAllAcceptedBills();
        model.addAttribute("bills", bills);
        return "bills";
    }

    @PostMapping("/bills")
    public String deleteBill(@RequestParam(value = "billId") String billId){
        billManager.deleteBillById(Long.parseLong(billId));
        log.info("Delete bill with id: " + billId);
        return "redirect:/bills";
    }
}

