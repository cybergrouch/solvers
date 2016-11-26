package org.lange.experiments.solver.cocosolver.currencytransfer.models;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by lange on 25/11/16.
 */
public class CurrencyTest {

    @Test
    public void testInstance_USD() {
        Currency currency1 = Currency.USD;
        assertNotNull(currency1);
        assertEquals(2, currency1.getMinorUnits());

        Optional<Currency> currencyOptional = Currency.resolve("USD");
        assertNotNull(currencyOptional);
        assertTrue(currencyOptional.isPresent());
        Currency currency2 = currencyOptional.get();
        assertNotNull(currency2);
        assertEquals(2, currency2.getMinorUnits());

        assertEquals(currency1, currency2);
    }

    @Test
    public void testInstance_PHP() {
        Currency currency1 = Currency.PHP;
        assertNotNull(currency1);
        assertEquals(2, currency1.getMinorUnits());

        Optional<Currency> currencyOptional = Currency.resolve("PHP");
        assertNotNull(currencyOptional);
        assertTrue(currencyOptional.isPresent());
        Currency currency2 = currencyOptional.get();
        assertNotNull(currency2);
        assertEquals(2, currency2.getMinorUnits());

        assertEquals(currency1, currency2);
    }

    @Test
    public void testInstance_AUD() {
        Currency currency1 = Currency.AUD;
        assertNotNull(currency1);
        assertEquals(2, currency1.getMinorUnits());

        Optional<Currency> currencyOptional = Currency.resolve("AUD");
        assertNotNull(currencyOptional);
        assertTrue(currencyOptional.isPresent());
        Currency currency2 = currencyOptional.get();
        assertNotNull(currency2);
        assertEquals(2, currency2.getMinorUnits());

        assertEquals(currency1, currency2);
    }

    @Test
    public void testInstance_SGD() {
        Currency currency1 = Currency.SGD;
        assertNotNull(currency1);
        assertEquals(2, currency1.getMinorUnits());

        Optional<Currency> currencyOptional = Currency.resolve("SGD");
        assertNotNull(currencyOptional);
        assertTrue(currencyOptional.isPresent());
        Currency currency2 = currencyOptional.get();
        assertNotNull(currency2);
        assertEquals(2, currency2.getMinorUnits());

        assertEquals(currency1, currency2);
    }

    @Test
    public void testInstance_JOD() {
        Currency currency1 = Currency.JOD;
        assertNotNull(currency1);
        assertEquals(3, currency1.getMinorUnits());

        Optional<Currency> currencyOptional = Currency.resolve("JOD");
        assertNotNull(currencyOptional);
        assertTrue(currencyOptional.isPresent());
        Currency currency2 = currencyOptional.get();
        assertNotNull(currency2);
        assertEquals(3, currency2.getMinorUnits());

        assertEquals(currency1, currency2);
    }

    @Test
    public void testInstance_JPY() {
        Currency currency1 = Currency.JPY;
        assertNotNull(currency1);
        assertEquals(0, currency1.getMinorUnits());

        Optional<Currency> currencyOptional = Currency.resolve("JPY");
        assertNotNull(currencyOptional);
        assertTrue(currencyOptional.isPresent());
        Currency currency2 = currencyOptional.get();
        assertNotNull(currency2);
        assertEquals(0, currency2.getMinorUnits());

        assertEquals(currency1, currency2);
    }

    @Test
    public void testInstance_EUR() {
        Currency currency1 = Currency.EUR;
        assertNotNull(currency1);
        assertEquals(2, currency1.getMinorUnits());

        Optional<Currency> currencyOptional = Currency.resolve("EUR");
        assertNotNull(currencyOptional);
        assertTrue(currencyOptional.isPresent());
        Currency currency2 = currencyOptional.get();
        assertNotNull(currency2);
        assertEquals(2, currency2.getMinorUnits());

        assertEquals(currency1, currency2);
    }

    @Test
    public void testInstance_CAD() {
        Optional<Currency> currencyOptional = Currency.resolve("CAD");
        assertNotNull(currencyOptional);
        assertFalse(currencyOptional.isPresent());
    }
}
