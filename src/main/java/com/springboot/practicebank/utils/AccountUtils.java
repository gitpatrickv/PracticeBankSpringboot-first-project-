package com.springboot.practicebank.utils;

import java.time.Year;

public class AccountUtils {

    public static String generateAccountNumber(){

        Year currentYear = Year.now();
        int min = 1000000;
        int max = 9999999;

        int randNumber = (int) Math.floor(Math.random() * (max - min + 1) + min);

        StringBuilder accountNumber = new StringBuilder();

        return String.valueOf(accountNumber.append(currentYear).append(randNumber));
    }





}
