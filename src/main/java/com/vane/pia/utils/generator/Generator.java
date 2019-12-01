package com.vane.pia.utils.generator;

import com.vane.pia.configuration.Values;
import org.apache.commons.lang3.RandomStringUtils;

public class Generator {

    private Generator() {
        // library class
    }

    /**
     * This function generate username with
     * letters and numbers of specific length.
     *
     * @return String random string
     */
    public static String generateUsername() {

        boolean useLetters = true;
        boolean useNumbers = true;
        return RandomStringUtils.random(Values.USERNAME_LENGTH, useLetters, useNumbers);
    }

    /**
     * This function generate password with
     * numbers only of specific length.
     *
     * @return String random pin
     */
    public static String generatePassword() {
        boolean useLetters = false;
        boolean useNumbers = true;
        return RandomStringUtils.random(Values.PASSWORD_LENGTH, useLetters, useNumbers);
    }
}
