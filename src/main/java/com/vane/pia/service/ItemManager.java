package com.vane.pia.service;

import com.vane.pia.domain.Item;

public interface ItemManager {

    void addItem(Item item);

    void deleteItemWithId(Long id);
}
