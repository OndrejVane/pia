package com.vane.pia.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Cannot cast to long, Exception UID: " + LongParseException.serialVersionUID) //404
@Slf4j
public class LongParseException extends RuntimeException {

    public static final long serialVersionUID = -3339463746834265371L;

    public LongParseException(String id){
        super("String: "+ id+" cannot cast to Long");
        log.error("String: "+ id+" cannot cast to Long, Serial UID: " + serialVersionUID);
    }

}
