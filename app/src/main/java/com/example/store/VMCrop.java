package com.example.store;

import java.text.NumberFormat;
import java.util.Locale;

public class VMCrop {
     public static String setFormatMoney(int amount){
        Locale vietnamLocale = new Locale("vi", "VN");
        NumberFormat vietnamFormat = NumberFormat.getCurrencyInstance(vietnamLocale);
        String vietnamFormattedAmount = vietnamFormat.format(amount);
        return vietnamFormattedAmount;
//        System.out.println("Vietnam locale: " + vietnamFormattedAmount);
    }
}
