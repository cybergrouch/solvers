package org.lange.experiments.solver.cocosolver.currencytransfer.models;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by lange on 25/11/16.
 */
public class TimeUtilTest {

    @Test
    public void testTimeUtil_toUtcDateTime() {
        DateTime dateTime = TimeUtil.toUtcDateTime
                .apply(2016)
                .apply(11)
                .apply(30)
                .apply(21)
                .apply(30);
        assertNotNull(dateTime);
        assertEquals(2016, dateTime.getYear());
        assertEquals(11, dateTime.getMonthOfYear());
        assertEquals(30, dateTime.getDayOfMonth());
        assertEquals(13, dateTime.getHourOfDay());
        assertEquals(30, dateTime.getMinuteOfHour());
        assertEquals(0, dateTime.getSecondOfMinute());
        assertEquals(0, dateTime.getMillisOfSecond());
        assertEquals(DateTimeZone.UTC, dateTime.getZone());
    }
}
