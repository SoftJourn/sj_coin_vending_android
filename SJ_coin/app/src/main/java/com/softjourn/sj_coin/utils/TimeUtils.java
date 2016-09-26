package com.softjourn.sj_coin.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Andriy Ksenych on 16.09.2016.
 */
public class TimeUtils {

    public static Date getDateFromString(String dateString){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return date;
    }

    public static String getPrettyTime(String dateString){
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date date = getDateFromString(dateString);

        return date == null ? dateString : format.format(date);
    }
}
