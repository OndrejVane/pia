package com.vane.pia.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Item not found, Exception UID: " + UserNotFoundException.serialVersionUID) //404
@Slf4j
public class ItemNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -8976292346704273947L;

    public ItemNotFoundException(Long id){
        super("Item with id: "+ id+" Not Found");
        log.error("Item with id: "+ id+" Not Found. Serial UID: " + serialVersionUID);
    }

}
