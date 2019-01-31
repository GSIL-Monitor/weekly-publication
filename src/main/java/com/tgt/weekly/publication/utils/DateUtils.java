package com.tgt.weekly.publication.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: ltl
 * @Date: 2018/12/18
 * @Time: 17:04
 * @Description: To change this template use File | Settings | File Templates.
 **/
@Slf4j
public class DateUtils {

    public static long getLastWeekBegin (){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int dayofweek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        calendar.add(Calendar.DATE, 2 - dayofweek - 7);
        calendar.setTimeZone(TimeZone.getDefault());
        dayBegin(calendar);
        return calendar.getTimeInMillis();
    }

    public static long getLastWeekEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_WEEK, 2);
        dayBegin(calendar);
        return calendar.getTimeInMillis() - 1;
    }

    public static long getYesterday (){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,-1);
        calendar.setTimeZone(TimeZone.getDefault());
        dayBegin(calendar);
        return calendar.getTimeInMillis();
    }

    public static long getToday (){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.setTimeZone(TimeZone.getDefault());
        dayBegin(calendar);
        return calendar.getTimeInMillis() - 1;
    }

    public static void dayBegin(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    public static int getMonth(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getYear(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static String getStringDay(long timestamp){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(timestamp));
        return String.format("%d%d%d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH )+ 1, calendar.get(Calendar.DATE));
    }

    public static String getMinuteDay(long timestamp){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(timestamp));
        return String.format("%02d%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
    }

    public static String getHourDay(long timestamp){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(timestamp));
        return String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY));
    }

    public static String getSecondDay(long timestamp){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(timestamp));
        return String.format("%02d%02d%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
    }

    public static String getHourAndMinute(long timestamp){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(timestamp));
        return String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
    }

    public static String getDateToStringStyle(String formatString, Date dates) {
        DateFormat dateFormatter = null;
        Date date = new Date();
        String dateFormat = "";
        if (StringUtils.isEmpty(formatString)) {
            formatString = "MM-dd HH:mm:ss";
        }
        if (date != null) {
            date = dates;
        }
        try {
            dateFormatter = new SimpleDateFormat(formatString);
            dateFormatter.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            dateFormat = dateFormatter.format(date);
        } catch (Exception e) {
            log.info("exception",e);
        }
        return dateFormat;
    }

}
