package com.vane.pia.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="User not found, Exception UID: " + UserNotFoundException.serialVersionUID) //404
@Slf4j
public class UserNotFoundException extends RuntimeException {

    public static final long serialVersionUID = -3332292346834265371L;

    public UserNotFoundException(Long id){
        super("User with id: "+ id+" Not Found");
        log.error("User with id: "+ id+" Not Found. Serial UID: " + serialVersionUID);
    }

}
