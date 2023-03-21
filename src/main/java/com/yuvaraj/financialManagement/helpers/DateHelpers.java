package com.yuvaraj.financialManagement.helpers;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Minutes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Slf4j
public class DateHelpers {


    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String TIME_ZONE = "Asia/Kuala_Lumpur";

    public static Date nowDate() {
        return new DateTime(DateTimeZone.forID(TIME_ZONE)).toDate();
    }

    public static DateTime nowDateTime() {
        return new DateTime(DateTimeZone.forID(TIME_ZONE));
    }

    public static Calendar nowCalendar() {
        return Calendar.getInstance(TimeZone.getTimeZone(TIME_ZONE));
    }

    public static Calendar nowCalendarStartOfTheDay() {
        Calendar calendar = nowCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        return calendar;
    }

    public static Calendar nowCalendarEndOfTheDay() {
        Calendar calendar = nowCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
        return calendar;
    }

    public static void setCalendarEndOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
    }

    public static String convertDateForEndResult(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        return simpleDateFormat.format(date);
    }

    public static Date nowDateAddMinutes(int minutes) {
        DateTime nowDateTime = nowDateTime();
        return nowDateTime.plusMinutes(minutes).toDate();
    }

    public static Date nowDateAddSeconds(int seconds) {
        DateTime nowDateTime = nowDateTime();
        return nowDateTime.plusSeconds(seconds).toDate();
    }

    public static Date dateAddMinutes(Date date, int minutes) {
        DateTime nowDateTime = new DateTime(date.getTime());
        return nowDateTime.plusMinutes(minutes).toDate();
    }

    public static int getMinutesLeft(Date date1, Date date2) {
        DateTime date1DateTime = new DateTime(date1.getTime());
        DateTime date2DateTime = new DateTime(date2.getTime());
        return Minutes.minutesBetween(date1DateTime, date2DateTime).getMinutes();
    }

    public static Date dateAddSeconds(Date date, int seconds) {
        DateTime nowDateTime = new DateTime(date.getTime());
        return nowDateTime.plusSeconds(seconds).toDate();
    }


    public static Date convertStringToDate(String date, String dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        simpleDateFormat.setLenient(true);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            log.warn("Unable to parse requestedDate={}, dateFormat={}, errorMessage={}", date, dateFormat, e.getMessage());
            return null;
        }
    }

    public static Date getStartDateOfTheWeek() {
        Calendar calendar = nowCalendarStartOfTheDay();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        return calendar.getTime();
    }

    public static Date getEndDateOfTheWeek(Date date) {
        Calendar calendar = nowCalendar();
        calendar.setTime(date);
        setCalendarEndOfDay(calendar);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    public static Date getStartDateOfTheMonth() {
        Calendar calendar = nowCalendarStartOfTheDay();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static Date getEndDateOfTheMonth(Date date) {
        Calendar calendar = nowCalendar();
        calendar.setTime(date);
        setCalendarEndOfDay(calendar);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    public static Date getStartDateOfTheYear() {
        Calendar calendar = nowCalendarStartOfTheDay();
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();
    }

    public static Date getEndDateOfTheyYear(Date date) {
        Calendar calendar = nowCalendar();
        calendar.setTime(date);
        setCalendarEndOfDay(calendar);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        return calendar.getTime();
    }
}
