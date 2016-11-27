package org.lange.experiments.solver.models;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Optional;
import java.util.function.Function;

/**
 * Created by lange on 25/11/16.
 */
public class TimeUtil {

    public static final Function<DateTime, DateTime> adjustToUtc = dateTime -> dateTime.withZone(DateTimeZone.UTC);

    public static final Function<Integer, Function<Integer, Function<Integer, Function<Integer, Function<Integer, DateTime>>>>>
            toUtcDateTime = (Integer year) -> (Integer month) -> (Integer date) -> (Integer hour) -> (Integer minute) ->
            Optional.of(new DateTime()
                    .withYear(year)
                    .withMonthOfYear(month)
                    .withDayOfMonth(date)
                    .withHourOfDay(hour)
                    .withMinuteOfHour(minute)
                    .withSecondOfMinute(0)
                    .withMillisOfSecond(0))
                    .map(adjustToUtc)
                    .orElse(null);
}


