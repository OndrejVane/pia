package com.vane.pia.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Contact not found, Exception UID: " + UserNotFoundException.serialVersionUID) //404
@Slf4j
public class ContactNotFoundException extends RuntimeException {

    public static final long serialVersionUID = -3332292346834432171L;

    public ContactNotFoundException(Long id){
        super("Contact with id: "+ id+" Not Found");
        log.error("Contact with id: "+ id+" Not Found. Serial UID: " + serialVersionUID);
    }

}
