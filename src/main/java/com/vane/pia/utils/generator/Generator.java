package com.vane.pia.utils.generator;

import com.vane.pia.configuration.Values;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Calendar;

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

    /**
     * Generate bill number which has format (FP | FV & YYYYCCCC ).
     *
     * @param newBillNumber number of bills
     * @param isAccepted is accepted or issued bill
     * @return bill number with specific pattern
     */
    public static String generateBillNumber(Integer newBillNumber, Boolean isAccepted){
        String billNumber;
        Integer year = Calendar.getInstance().get(Calendar.YEAR);
        if (isAccepted){
            billNumber = "FP" + year.toString() + String.format("%04d", newBillNumber);
        }else {
            billNumber = "FV" + year.toString() + String.format("%04d", newBillNumber);
        }

        return billNumber;
    }
}
