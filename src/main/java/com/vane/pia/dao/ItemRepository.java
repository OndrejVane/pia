package com.vane.pia.dao;

import com.vane.pia.domain.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {

    Item findByName(String name);
}
