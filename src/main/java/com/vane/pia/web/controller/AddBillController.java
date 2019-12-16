package com.vane.pia.web.controller;

import com.vane.pia.configuration.Pages;
import com.vane.pia.domain.Bill;
import com.vane.pia.domain.Item;
import com.vane.pia.domain.TempItem;
import com.vane.pia.exception.LastAdminDeletingException;
import com.vane.pia.service.BillManager;
import com.vane.pia.service.ContactManager;
import com.vane.pia.service.TempItemManager;
import com.vane.pia.utils.Calculator;
import com.vane.pia.utils.ItemConverter;
import com.vane.pia.utils.LongParser;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.AssociationOverride;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class AddBillController {

    @Autowired
    ContactManager contactManager;

    @Autowired
    TempItemManager tempItemManager;

    @Autowired
    BillManager billManager;


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping(Pages.ADD_BILL_ITEM_PAGE)
    public String showAddBillPage(Model model) {
        List<TempItem> items = tempItemManager.getAllTempItemsForCurrentUser();
        model.addAttribute("contacts", contactManager.getAllContacts());
        model.addAttribute("tempItem", new TempItem());
        model.addAttribute("bill", new Bill());
        model.addAttribute("items", items);
        model.addAttribute("total", Calculator.calculateTotalPrice(items));
        return Pages.ADD_ITEMS_TEMPLATE;
    }

    @PostMapping("/bills/add/item")
    public String addItem(@Valid TempItem newTempItem,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            log.warn("BINDING RESULT ERROR");
            List<TempItem> items = tempItemManager.getAllTempItemsForCurrentUser();
            model.addAttribute("items", items);
            model.addAttribute("total", Calculator.calculateTotalPrice(items));
            model.addAttribute("contacts", contactManager.getAllContacts());
            model.addAttribute("bill", new Bill());
            return Pages.ADD_ITEMS_TEMPLATE;
        }
        tempItemManager.addTempItem(newTempItem);
        return "redirect:/bills/add/item";
    }

    @PostMapping("/bills/add/item/{tempItemId}")
    public String deleteItem(@PathVariable String tempItemId){
        tempItemManager.deleteTempItemWithId(LongParser.parseLong(tempItemId));
        log.info("Delete temp item with id: " + tempItemId);
        return "redirect:/bills/add/item";
    }

    @PostMapping("/bills/add/bill")
    public String addNewBill(@Valid Bill bill, BindingResult bindingResult, Model model){
        List<TempItem> items = tempItemManager.getAllTempItemsForCurrentUser();
        if (bindingResult.hasErrors()) {
            log.warn("BINDING RESULT ERROR");
            model.addAttribute("items", items);
            model.addAttribute("total", Calculator.calculateTotalPrice(items));
            model.addAttribute("contacts", contactManager.getAllContacts());
            model.addAttribute("tempItem", new TempItem());
            return Pages.ADD_ITEMS_TEMPLATE;
        }

        if(items.size() == 0){
            return "redirect:/bills/add/item?error";
        }

        bill.setItems(ItemConverter.convertItems(items));
        billManager.addBill(bill);
        return "redirect:/bills?success";
    }
}
