package org.lange.experiments.solver.cocosolver.currencytransfer.models;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by lange on 25/11/16.
 */
public class MonetaryAmountTest {

    @Test
    public void testBuilder() {
        MonetaryAmount.Builder builder = MonetaryAmount.Builder.create();
        assertNotNull(builder);
        assertTrue(ModelBuilder.class.isAssignableFrom(builder.getClass()));

        Optional<MonetaryAmount> monetaryAmountOptional = builder.build();
        assertNotNull(monetaryAmountOptional);
        assertFalse(monetaryAmountOptional.isPresent());
    }

    @Test
    public void testValidProperties() {
        MonetaryAmount.Builder builder = MonetaryAmount.Builder.create().currency(Currency.USD).amount(10000L);
        assertNotNull(builder);

        Optional<MonetaryAmount> monetaryAmountOptional = builder.build();
        assertNotNull(monetaryAmountOptional);
        assertTrue(monetaryAmountOptional.isPresent());

        MonetaryAmount monetaryAmount = monetaryAmountOptional.get();
        assertNotNull(monetaryAmount);
        assertEquals(Currency.USD, monetaryAmount.getCurrency());
        assertEquals((Long) 10000L, monetaryAmount.getAmount());

        monetaryAmount.setCurrency(Currency.AUD);
        assertEquals(Currency.AUD, monetaryAmount.getCurrency());

        monetaryAmount.setAmount(20000L);
        assertEquals((Long) 20000L, monetaryAmount.getAmount());
    }

    @Test
    public void testInvalidProperties() {
        MonetaryAmount.Builder builder = MonetaryAmount.Builder.create();
        assertNotNull(builder);

        Optional<MonetaryAmount> monetaryAmountOptional = builder.build();
        assertNotNull(monetaryAmountOptional);
        assertFalse(monetaryAmountOptional.isPresent());


        Optional<MonetaryAmount> monetaryAmountOptional2 = builder.currency(Currency.USD).build();
        assertNotNull(monetaryAmountOptional2);
        assertFalse(monetaryAmountOptional2.isPresent());

        Optional<MonetaryAmount> monetaryAmountOptional3 = builder.currency(Currency.USD).amount(10000L).build();
        assertNotNull(monetaryAmountOptional3);
        assertTrue(monetaryAmountOptional3.isPresent());

        MonetaryAmount monetaryAmount = monetaryAmountOptional3.get();
        assertNotNull(monetaryAmount);
        assertEquals(Currency.USD, monetaryAmount.getCurrency());
        assertEquals((Long) 10000L, monetaryAmount.getAmount());
    }
}
