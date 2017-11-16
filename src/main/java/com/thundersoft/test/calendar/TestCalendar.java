package com.thundersoft.test.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestCalendar {

    public static void main(String[] args) throws ParseException {
        System.err.println(getTimesmorning());
        System.err.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(getTimesmorning())));
    }

    public static long getTimesmorning() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(new SimpleDateFormat("HH").format(new Date())));
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }
}
