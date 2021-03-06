package org.lange.experiments.solver.models;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by lange on 26/11/16.
 */
public class DateTimeBuilderTest {

    @Test
    public void testInstantiation() {
        DateTimeBuilder builder = DateTimeBuilder.create();
        assertNotNull(builder);
        assertTrue(ModelBuilder.class.isAssignableFrom(builder.getClass()));

        Optional<DateTime> buildOutputOptional = builder.build();
        assertNotNull(buildOutputOptional);
        assertFalse(buildOutputOptional.isPresent());
    }

    @Test
    public void testProperties() {
        DateTimeBuilder builder = DateTimeBuilder.create();
        assertNotNull(builder);

        Optional<DateTime> buildOutputOptional = builder.build();
        assertNotNull(buildOutputOptional);
        assertFalse(buildOutputOptional.isPresent());

        Optional<DateTime> buildOutputOptional2 = builder.year(2016).build();
        assertNotNull(buildOutputOptional2);
        assertFalse(buildOutputOptional2.isPresent());

        Optional<DateTime> buildOutputOptional3 = builder.year(2016).month(1).build();
        assertNotNull(buildOutputOptional3);
        assertFalse(buildOutputOptional3.isPresent());

        Optional<DateTime> buildOutputOptional4 = builder.year(2016).month(1).date(15).build();
        assertNotNull(buildOutputOptional4);
        assertFalse(buildOutputOptional4.isPresent());

        Optional<DateTime> buildOutputOptional5 = builder.year(2016).month(1).date(15).hour(10).build();
        assertNotNull(buildOutputOptional5);
        assertFalse(buildOutputOptional5.isPresent());

        Optional<DateTime> buildOutputOptional6 = builder.year(2016).month(1).date(15).hour(10).minute(45).build();
        assertNotNull(buildOutputOptional6);
        assertTrue(buildOutputOptional6.isPresent());

        DateTime dateTime = buildOutputOptional6.get();
        assertNotNull(dateTime);
        Assert.assertEquals(2016, dateTime.getYear());
        Assert.assertEquals(1, dateTime.getMonthOfYear());
        Assert.assertEquals(15, dateTime.getDayOfMonth());
        Assert.assertEquals(2, dateTime.getHourOfDay());
        Assert.assertEquals(45, dateTime.getMinuteOfHour());
        Assert.assertEquals(0, dateTime.getSecondOfMinute());
        Assert.assertEquals(0, dateTime.getMillisOfSecond());
        Assert.assertEquals(DateTimeZone.UTC, dateTime.getZone());
    }
}
