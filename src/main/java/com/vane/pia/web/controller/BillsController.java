package com.vane.pia.web.controller;

import com.vane.pia.configuration.Pages;
import com.vane.pia.domain.Bill;
import com.vane.pia.domain.Item;
import com.vane.pia.domain.TempItem;
import com.vane.pia.domain.User;
import com.vane.pia.service.BillManager;
import com.vane.pia.service.ContactManager;
import com.vane.pia.service.ItemManager;
import com.vane.pia.utils.Calculator;
import com.vane.pia.utils.LongParser;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
public class BillsController {

    @Autowired
    private BillManager billManager;

    @Autowired
    private ContactManager contactManager;

    @Autowired
    private ItemManager itemManager;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping("/bills")
    public String showAllBills(Model model) {
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

    @PreAuthorize("hasRole('ACCOUNTANT')")
    @PostMapping("/bills")
    public String deleteBill(@RequestParam(value = "billId") String billId){
        billManager.deleteBillById(Long.parseLong(billId));
        log.info("Delete bill with id: " + billId);
        return "redirect:/bills";
    }

    @GetMapping("/bills/{billId}")
    public String showBillDetails(@PathVariable String billId, Model model){
        Bill bill = billManager.getBillById(LongParser.parseLong(billId));
        model.addAttribute("bill", bill);
        model.addAttribute("items", bill.getItems());

        return Pages.SHOW_BILL_TEMPLATE;
    }

    @PreAuthorize("hasRole('ACCOUNTANT')")
    @GetMapping("/bills/{billId}/edit")
    public String showBillEditPage(@PathVariable String billId, Model model){
        Bill bill = billManager.getBillById(LongParser.parseLong(billId));
        model.addAttribute("bill", bill);
        model.addAttribute("item", new Item());
        model.addAttribute("contacts", contactManager.getAllContacts());
        model.addAttribute("total", Calculator.calculateTotalPriceForItems(bill.getItems()));

        return Pages.EDIT_BILL_TEMPLATE;
    }

    @PreAuthorize("hasRole('ACCOUNTANT')")
    @PostMapping("/bills/{billId}/edit")
    public String saveEditedBill(@PathVariable String billId,
                                 @Valid Bill bill,
                                 BindingResult bindingResult,
                                 Model model){
        Bill foundBill = billManager.getBillById(LongParser.parseLong(billId));
        if (bindingResult.hasErrors()) {
            log.warn("BINDING RESULT ERROR");
            model.addAttribute("bill", foundBill);
            model.addAttribute("contacts", contactManager.getAllContacts());
            model.addAttribute("total", Calculator.calculateTotalPriceForItems(bill.getItems()));
            model.addAttribute("item", new Item());
            return Pages.EDIT_BILL_TEMPLATE;
        }

        billManager.updateBill(bill, LongParser.parseLong(billId));
        return "redirect:/bills/" + billId + "?success";
    }

    @PreAuthorize("hasRole('ACCOUNTANT')")
    @PostMapping("/bills/{billId}/edit/{itemId}")
    public String deleteItem(@PathVariable String billId,
                             @PathVariable String itemId){
        Bill bill = billManager.getBillById(LongParser.parseLong(billId));
        if(bill.getItems().size() == 1){
            return "redirect:/bills/" + billId + "/edit?error";
        }

        itemManager.deleteItemWithId(LongParser.parseLong(itemId));

        return "redirect:/bills/" + billId + "/edit";
    }

    @PreAuthorize("hasRole('ACCOUNTANT')")
    @PostMapping("/bills/{billId}/edit/addItem")
    public String addItemToBill(@PathVariable String billId,
                                @Valid Item item,
                                BindingResult bindingResult,
                                Model model){

        Bill bill = billManager.getBillById(LongParser.parseLong(billId));

        if (bindingResult.hasErrors()) {
            log.warn("BINDING RESULT ERROR");
            model.addAttribute("bill", bill);
            model.addAttribute("contacts", contactManager.getAllContacts());
            model.addAttribute("total", Calculator.calculateTotalPriceForItems(bill.getItems()));
            return Pages.EDIT_BILL_TEMPLATE;
        }
        item.setBill(bill);
        itemManager.addItem(item);

        return "redirect:/bills/"+billId+"/edit";
    }
}

