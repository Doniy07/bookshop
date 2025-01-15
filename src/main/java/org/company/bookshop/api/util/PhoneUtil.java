package org.company.bookshop.api.util;

import org.company.bookshop.api.exception.BadRequestException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtil {

    public static void isCorrectPhone(String phone) {
        if (!phone.startsWith("998") && !phone.startsWith("+998")) {
            phone = "998" + phone;
        }
        if (!phone.startsWith("+")) {
            phone = "+" + phone;
        }
        String pattern = "^\\+998\\d{9}$";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(phone);
        if (!matcher.matches())
            throw new BadRequestException("Phone number is incorrect");
    }
}

