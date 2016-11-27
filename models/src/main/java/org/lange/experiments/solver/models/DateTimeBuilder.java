package org.lange.experiments.solver.models;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by lange on 25/11/16.
 */
public class DateTimeBuilder implements ModelBuilder<DateTime> {

    private enum ParameterDescriptor {
        YEAR, MONTH, DATE, HOUR, MINUTE;
    }

    private Map<ParameterDescriptor, Function<DateTime, DateTime>> parameters;

    private DateTimeBuilder() {
        super();
        parameters = new HashMap<>();
    }

    public DateTimeBuilder year(int year) {
        parameters.put(ParameterDescriptor.YEAR, dateTime -> dateTime.withYear(year));
        return this;
    }

    public DateTimeBuilder month(int month) {
        parameters.put(ParameterDescriptor.MONTH, dateTime -> dateTime.withMonthOfYear(month));
        return this;
    }

    public DateTimeBuilder date(int date) {
        parameters.put(ParameterDescriptor.DATE, dateTime -> dateTime.withDayOfMonth(date));
        return this;
    }

    public DateTimeBuilder hour(int hour) {
        parameters.put(ParameterDescriptor.HOUR, dateTime -> dateTime.withHourOfDay(hour));
        return this;
    }

    public DateTimeBuilder minute(int minute) {
        parameters.put(ParameterDescriptor.MINUTE, dateTime -> dateTime.withMinuteOfHour(minute));
        return this;
    }

    @Override
    public Stream<Function<DateTime, ?>> getFieldExtractors() {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public DateTime getBaseObject() {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public Optional<DateTime> build() {
        if (Arrays.stream(ParameterDescriptor.values()).map(descriptor -> parameters.containsKey(descriptor)).anyMatch(Boolean.FALSE::equals)) {
            return Optional.empty();
        }
        return parameters
                .values()
                .stream()
                .reduce(Function::andThen)
                .map(function -> function.apply(new DateTime()))
                .map(dateTime ->
                        dateTime
                                .withZone(DateTimeZone.UTC)
                                .withSecondOfMinute(0)
                                .withMillisOfSecond(0));
    }

    public static DateTimeBuilder create() {
        return new DateTimeBuilder();
    }
}
