package com.vane.pia.service;

import com.vane.pia.domain.TempItem;

import java.util.List;

public interface TempItemManager {

    void addTempItem(TempItem tempItem);

    List<TempItem> getAllTempItemsForCurrentUser();

    void deleteTempItemWithId(Long id);
}
