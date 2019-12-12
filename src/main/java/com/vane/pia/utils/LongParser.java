package com.vane.pia.utils;

import com.vane.pia.exception.LongParseException;

public class LongParser {

    public static Long parseLong(String string){
        try {
            return Long.parseLong(string);
        }catch (NumberFormatException e){
            throw new LongParseException(string);
        }
    }
}
