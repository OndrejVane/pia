package com.vane.pia.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Bill not found, Exception UID: " + UserNotFoundException.serialVersionUID) //404
@Slf4j
public class BillNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -8976292346834265371L;

    public BillNotFoundException(Long id){
        super("Bill with id: "+ id+" Not Found");
        log.error("Bill with id: "+ id+" Not Found. Serial UID: " + serialVersionUID);
    }

}
