package com.vane.pia.utils;

import com.vane.pia.domain.Item;
import com.vane.pia.domain.TempItem;

import java.util.ArrayList;
import java.util.List;

public class ItemConverter {

    public static List<Item> convertItems(List<TempItem> tempItems) {
        List<Item> items = new ArrayList<>();

        for (TempItem tempItem : tempItems) {
            Item item = new Item();
            item.setName(tempItem.getName());
            item.setDescription(tempItem.getDescription());
            item.setPrice(tempItem.getPrice());
            item.setCount(tempItem.getCount());
            item.setVatPercentage(tempItem.getVatPercentage());
            item.setDeleted(false);
            item.setVatAmount(tempItem.getVatAmount());
            item.setTotalPrice(tempItem.getTotalPrice());
            item.setPriceWithoutVat(tempItem.getPriceWithoutVat());
            items.add(item);

        }
        return items;
    }
}
