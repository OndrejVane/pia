package com.vane.pia.service;

import com.vane.pia.dao.BillRepository;
import com.vane.pia.dao.ItemRepository;
import com.vane.pia.domain.Bill;
import com.vane.pia.domain.Item;
import com.vane.pia.exception.ItemNotFoundException;
import com.vane.pia.utils.Calculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ItemManagerImpl implements ItemManager {

    private ItemRepository itemRepository;
    private BillRepository billRepository;


    @Autowired
    public ItemManagerImpl(ItemRepository itemRepository, BillRepository billRepository) {
        this.itemRepository = itemRepository;
        this.billRepository = billRepository;
    }

    @EventListener(classes = {
            ContextRefreshedEvent.class
    })
    @Order(6)
    @Transactional
    public void setup() {
        if (itemRepository.count() == 0) {
            log.info("No items found.");
            List<Item> items = new LinkedList<>();
            Bill bill1 = this.billRepository.findByName("Bill 1");
            Bill bill2 = this.billRepository.findByName("Bill 2");
            Bill bill3 = this.billRepository.findByName("Bill 3");
            Bill bill4 = this.billRepository.findByName("Bill 4");
            Bill bill5 = this.billRepository.findByName("Bill 5");
            Bill bill6 = this.billRepository.findByName("Bill 6");


            Item item1 = new Item("Item 1", "Description", 100f, 3f, 21);
            item1.setBill(bill1);
            items.add(item1);
            this.addItem(item1);


            Item item2 = new Item("Item 2", "Description", 120f, 9f, 15);
            item2.setBill(bill1);
            items.add(item2);

            Item item3 = new Item("Item 3", "Description", 10f, 1f, 10);
            item3.setBill(bill1);
            items.add(item3);

            Item item4 = new Item("Item 4", "Description", 1000f, 1f, 10);
            item4.setBill(bill1);
            items.add(item4);

            Item item5 = new Item("Item 5", "Description", 1000f, 1f, 10);
            item5.setBill(bill2);
            items.add(item5);

            Item item6 = new Item("Item 6", "Description", 1000f, 1f, 10);
            item6.setBill(bill2);
            items.add(item6);

            Item item7 = new Item("Item 7", "Description", 1000f, 1f, 10);
            item7.setBill(bill3);
            items.add(item7);

            Item item8 = new Item("Item 8", "Description", 1000f, 1f, 10);
            item8.setBill(bill4);
            items.add(item8);

            Item item9 = new Item("Item 9", "Description", 1000f, 1f, 10);
            item9.setBill(bill5);
            items.add(item9);

            Item item10 = new Item("Item 10", "Description", 1000f, 1f, 10);
            item10.setBill(bill6);
            items.add(item10);

            for (Item item : items) {
                this.addItem(item);
            }
        }
    }

    @Override
    public void addItem(Item item) {
        Calculator.calculateFieldForItem(item);
        this.itemRepository.save(item);
    }

    @Override
    @Transactional
    public void deleteItemWithId(Long id) {
        Optional<Item> item = itemRepository.findById(id);
        if(item.isEmpty()){
            throw new ItemNotFoundException(id);
        }
        log.info("Deleting item with id: " + id);
        itemRepository.delete(item.get());
    }
}
