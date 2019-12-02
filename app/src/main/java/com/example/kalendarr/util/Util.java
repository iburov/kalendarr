package com.example.kalendarr.util;

import java.text.DateFormatSymbols;
import java.util.concurrent.atomic.AtomicInteger;

public class Util {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "eventdb";
    public static final String TABLE_NAME = "events";

    public static final String KEY_ID = "event_id";
    public static final String KEY_TITLE = "event_title";
    public static final String KEY_DESCRIPTION = "event_description";
    public static final String KEY_DATE = "event_date";

    public static String[] getSplitMonthString(String date) {
        String dateNumbers[] = date.split("/");
        return dateNumbers;
    }

    public static String getMonth(String date) {
        int monthNumber = Integer.parseInt(getSplitMonthString(date)[1]);
        return new DateFormatSymbols().getMonths()[monthNumber-1];
    }

    public static class Sequence {

        private static final AtomicInteger counter = new AtomicInteger();

        public static int nextValue() {
            return counter.getAndIncrement();
        }
    }
}
