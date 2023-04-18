package com.partyh.finder.common.validators.commons;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {
    public static String validationFormatLocalDateTime(String localDateTime, String nameField) {
        String message = "Format ".concat(nameField).concat(" is: yyyy-MM-dd[T or space]HH:mm:ss, minutes and seconds is not mandatory");
        localDateTime = localDateTime.replace(" ", "T");

        String regex = "(\\d){4}-(\\d){2}-(\\d){2}T(\\d){2}(:(\\d){2})?(:(\\d){2})?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(localDateTime);

        if(matcher.matches()) {
            int length = localDateTime.length();
            if(length == 13) {
                localDateTime = localDateTime.concat(":00:00");
            } else if(length == 16) {
                localDateTime = localDateTime.concat(":00");
            } else if (length != 19) {
                return message;
            }
        } else {
            return message;
        }
        return localDateTime;
    }

    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }



}
