package com.vane.pia.service;

import com.vane.pia.dao.TempItemRepository;
import com.vane.pia.dao.UserRepository;
import com.vane.pia.domain.TempItem;
import com.vane.pia.domain.User;
import com.vane.pia.exception.ItemNotFoundException;
import com.vane.pia.exception.UserNotFoundException;
import com.vane.pia.model.WebCredentials;
import com.vane.pia.utils.Calculator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TempItemManagerImpl implements TempItemManager {

    private final TempItemRepository tempItemRepository;
    private final UserRepository userRepository;

    @Autowired
    public TempItemManagerImpl(TempItemRepository tempItemRepository, UserRepository userRepository) {
        this.tempItemRepository = tempItemRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addTempItem(TempItem tempItem) {
        Optional<User> currentUser = userRepository.findById(getCurrentUser().getId());
        if (currentUser.isEmpty()) {
            throw new UserNotFoundException(getCurrentUser().getId());
        }
        Calculator.calculateFieldForTempItem(tempItem);
        tempItem.setUser(currentUser.get());
        this.tempItemRepository.save(tempItem);
    }

    @Override
    public List<TempItem> getAllTempItemsForCurrentUser() {
        WebCredentials currentUser = getCurrentUser();
        List<TempItem> emptyItems = new ArrayList<>();
        if (tempItemRepository.getAllByUserId(currentUser.getId()) == null) {
            return emptyItems;
        } else {
            return tempItemRepository.getAllByUserId(currentUser.getId());
        }
    }

    @Override
    @Transactional
    public void deleteTempItemWithId(Long id) {
        Optional<TempItem> tempItem = this.tempItemRepository.findById(id);
        if(tempItem.isEmpty()){
            throw new ItemNotFoundException(id);
        }
        this.tempItemRepository.delete(tempItem.get());
    }

    private WebCredentials getCurrentUser() {
        return (WebCredentials) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
