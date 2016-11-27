package org.lange.experiments.solver.models;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by lange on 27/11/16.
 */
public class InversibleRateTest {

    @Test
    public void testBuilder() {
        InversibleRate.Builder builder = InversibleRate.Builder.create();
        assertNotNull(builder);
        assertTrue(ModelBuilder.class.isAssignableFrom(builder.getClass()));

        Optional<InversibleRate> buildOptional = builder.build();
        assertNotNull(buildOptional);
        assertFalse(buildOptional.isPresent());
    }

    @Test
    public void testValidProperties() {
        InversibleRate.Builder builder = InversibleRate.Builder.create();
        assertNotNull(builder);
        assertTrue(ModelBuilder.class.isAssignableFrom(builder.getClass()));

        BigDecimal rate = new BigDecimal(49.820002);
        Optional<InversibleRate> buildOptional = builder.rate(rate).build();
        assertNotNull(buildOptional);
        assertTrue(buildOptional.isPresent());

        InversibleRate inversibleRate = buildOptional.get();
        assertFalse(inversibleRate.isInversed());
        assertEquals(rate.setScale(6, BigDecimal.ROUND_HALF_EVEN), inversibleRate.getRate());

        InversibleRate inversedRate = inversibleRate.getInversedRate();
        assertNotNull(inversedRate);
        assertTrue(inversedRate.isInversed());
        assertEquals(Optional.of(rate).map(InversibleRate.INVERSE).orElse(BigDecimal.ZERO), inversedRate.getRate());

        InversibleRate inversedInversedRate = inversedRate.getInversedRate();
        assertNotNull(inversedInversedRate);
        assertSame(inversibleRate, inversedInversedRate);
        assertEquals(rate.setScale(6, BigDecimal.ROUND_HALF_EVEN), inversedInversedRate.getRate());
        assertFalse(inversedInversedRate.isInversed());

        InversibleRate inversedInversedRateInversedRate = inversedInversedRate.getInversedRate();
        assertNotNull(inversedInversedRateInversedRate);
        assertSame(inversedRate, inversedInversedRateInversedRate);
        assertTrue(inversedInversedRateInversedRate.isInversed());
        assertEquals(Optional.of(rate).map(InversibleRate.INVERSE).orElse(BigDecimal.ZERO), inversedInversedRateInversedRate.getRate());
    }

    @Test
    public void testInvalidProperties() {
        InversibleRate.Builder builder = InversibleRate.Builder.create();
        assertNotNull(builder);
        assertTrue(ModelBuilder.class.isAssignableFrom(builder.getClass()));

        Optional<InversibleRate> buildOptional = builder.build();
        assertNotNull(buildOptional);
        assertFalse(buildOptional.isPresent());

        BigDecimal rate = new BigDecimal(49.820002);
        builder = builder.rate(rate);
        buildOptional = builder.build();
        assertNotNull(buildOptional);
        assertTrue(buildOptional.isPresent());
    }
}
