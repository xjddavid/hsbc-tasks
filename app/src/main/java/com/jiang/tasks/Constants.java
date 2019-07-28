package com.jiang.tasks;

import com.jiang.tasks.exceptions.DateParseException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Constants {
    private static final String datePattern = "ddMMyyyy";
    private static final String dateReturnPattern = "dd/MM/yyyy";

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

    public static String convertDateToString(Date date) throws DateParseException {
        return new SimpleDateFormat(Constants.dateReturnPattern).format(date);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
}
