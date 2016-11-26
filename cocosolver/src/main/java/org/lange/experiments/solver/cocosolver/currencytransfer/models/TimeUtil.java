package org.lange.experiments.solver.cocosolver.currencytransfer.models;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * Created by lange on 25/11/16.
 */
public class TimeUtil {
    public static DateTime createUTC(int year, int month, int date, int hour, int minute) {
        return new DateTime().withYear(year).withMonthOfYear(month).withDayOfMonth(date).withHourOfDay(hour).withMinuteOfHour(minute).withSecondOfMinute(0).withZone(DateTimeZone.UTC);
    }
}


