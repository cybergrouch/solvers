package org.lange.experiments.solver.cocosolver.currencytransfer.models;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by lange on 26/11/16.
 */
public class CurrencyPairTest {

    @Test
    public void testBuilder() {
        CurrencyPair.Builder builder = CurrencyPair.Builder.create();
        assertNotNull(builder);
        assertTrue(ModelBuilder.class.isAssignableFrom(builder.getClass()));

        Optional<CurrencyPair> buildOptional = builder.build();
        assertNotNull(buildOptional);
        assertFalse(buildOptional.isPresent());
    }

    @Test
    public void testCreate() {
        CurrencyPair currencyPair = CurrencyPair.create(Currency.PHP, Currency.EUR);
        assertNotNull(currencyPair);
        assertEquals("EUR-PHP", currencyPair.toString());
    }

    @Test
    public void testProperties() {
        CurrencyPair.Builder builder = CurrencyPair.Builder.create();
        assertNotNull(builder);

        Optional<CurrencyPair> buildOptional = builder.build();
        assertNotNull(buildOptional);
        assertFalse(buildOptional.isPresent());

        builder = builder.currencies(Currency.USD, Currency.EUR);

        buildOptional = builder.build();
        assertNotNull(buildOptional);
        assertTrue(buildOptional.isPresent());

        CurrencyPair currencyPair = buildOptional.orElse(null);
        Currency leftCurrency = currencyPair.getLeft();
        Currency rightCurrency = currencyPair.getRight();
        assertNotNull(leftCurrency);
        assertNotNull(rightCurrency);
        assertEquals(Currency.EUR, leftCurrency);
        assertEquals(Currency.USD, rightCurrency);
    }

    @Test
    public void testEquals() {
        Optional<CurrencyPair> pair1 = CurrencyPair.Builder.create().currencies(Currency.EUR, Currency.AUD).build();
        assertNotNull(pair1);
        assertTrue(pair1.isPresent());

        Optional<CurrencyPair> pair2 = CurrencyPair.Builder.create().currencies(Currency.AUD, Currency.EUR).build();
        assertNotNull(pair2);
        assertTrue(pair2.isPresent());

        Optional<CurrencyPair> pair3 = CurrencyPair.Builder.create().currencies(Currency.USD, Currency.EUR).build();
        assertNotNull(pair3);
        assertTrue(pair3.isPresent());

        assertEquals(pair1, pair2);
        assertEquals(pair1.get(), pair2.get());
        assertEquals(pair1.get().hashCode(), pair2.get().hashCode());

        assertNotEquals(pair1, pair3);
        assertNotEquals(pair1.get(), pair3.get());
        assertNotEquals(pair1.get().hashCode(), pair3.get().hashCode());
    }
}
