package com.jiang.tasks;

import com.jiang.tasks.exceptions.DateParseException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Constants {
    private static final String datePattern = "ddMMyyyy";

    public static Date convertStringToDate(String s) throws DateParseException {
        if (s.length() != 8) {
            System.out.println(s + "is not a valid date input.");
            throw new DateParseException(s);
        }
        try {
            return new SimpleDateFormat(Constants.datePattern).parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new DateParseException(s);
        }
    }
}
