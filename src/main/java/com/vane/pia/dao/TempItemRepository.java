package com.vane.pia.dao;

import com.vane.pia.domain.TempItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TempItemRepository extends CrudRepository<TempItem, Long> {

    List<TempItem> getAllByUserId(Long id);

    void deleteAllByUserId(Long id);
}
