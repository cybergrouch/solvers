package org.lange.experiments.solver.models;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Test;

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
        Assert.assertEquals(2016, dateTime.getYear());
        Assert.assertEquals(11, dateTime.getMonthOfYear());
        Assert.assertEquals(30, dateTime.getDayOfMonth());
        Assert.assertEquals(13, dateTime.getHourOfDay());
        Assert.assertEquals(30, dateTime.getMinuteOfHour());
        Assert.assertEquals(0, dateTime.getSecondOfMinute());
        Assert.assertEquals(0, dateTime.getMillisOfSecond());
        Assert.assertEquals(DateTimeZone.UTC, dateTime.getZone());
    }
}
