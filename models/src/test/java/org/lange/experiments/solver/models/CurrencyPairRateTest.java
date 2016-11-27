package org.lange.experiments.solver.models;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by lange on 27/11/16.
 */
public class CurrencyPairRateTest {

    @Test
    public void testInstantiation() {
        CurrencyPair pair = CurrencyPair.create(Currency.USD, Currency.PHP);
        BigDecimal conversionRate = new BigDecimal(49.820002);

        Optional<CurrencyPairRate> rateOptional = pair.createRate(Currency.USD, Currency.PHP, conversionRate);
        assertNotNull(rateOptional);
        assertTrue(rateOptional.isPresent());
        CurrencyPairRate currencyPairRate = rateOptional.get();
        assertNotNull(currencyPairRate);

        assertEquals(conversionRate.setScale(6, BigDecimal.ROUND_HALF_EVEN), currencyPairRate.getConversionRate(CurrencyPair.Direction.INVERSED));
        assertEquals(new BigDecimal(1.0).setScale(6, BigDecimal.ROUND_HALF_EVEN).divide(conversionRate, BigDecimal.ROUND_HALF_EVEN),
                currencyPairRate.getConversionRate(CurrencyPair.Direction.NORMAL));
    }

    @Test
    public void testBuilder() {
        CurrencyPairRate.Builder builder = CurrencyPairRate.Builder.create();
        assertNotNull(builder);
        assertTrue(ModelBuilder.class.isAssignableFrom(builder.getClass()));

        Optional<CurrencyPairRate> buildOptional = builder.build();
        assertNotNull(buildOptional);
        assertFalse(buildOptional.isPresent());
    }

    @Test
    public void testValidProperties() {
        Currency fromCurrency = Currency.USD;
        Currency toCurrency = Currency.PHP;
        BigDecimal conversionRate = new BigDecimal(49.820002);

        CurrencyPairRate.Builder builder = CurrencyPairRate.Builder.create().currencyPair(CurrencyPair.create(fromCurrency, toCurrency)).quotedRate(fromCurrency, toCurrency, conversionRate);
        assertNotNull(builder);

        Optional<CurrencyPairRate> buildOptional = builder.build();
        assertNotNull(buildOptional);
        assertTrue(buildOptional.isPresent());

        CurrencyPairRate currencyPairRate = buildOptional.get();
        assertNotNull(currencyPairRate);
        assertEquals(CurrencyPair.create(fromCurrency, toCurrency), currencyPairRate.getCurrencyPair());
        assertEquals(conversionRate.setScale(6, BigDecimal.ROUND_HALF_EVEN), currencyPairRate.getConversionRate(CurrencyPair.Direction.INVERSED));
        assertEquals(new BigDecimal(1.0).setScale(6, BigDecimal.ROUND_HALF_EVEN).divide(conversionRate, BigDecimal.ROUND_HALF_EVEN),
                currencyPairRate.getConversionRate(CurrencyPair.Direction.NORMAL));
    }

    @Test
    public void testInvalidProperties() {
        Currency fromCurrency = Currency.USD;
        Currency toCurrency = Currency.PHP;
        BigDecimal conversionRate = new BigDecimal(49.820002);

        CurrencyPairRate.Builder builder = CurrencyPairRate.Builder.create();
        assertNotNull(builder);
        Optional<CurrencyPairRate> buildOptional = builder.build();
        assertNotNull(buildOptional);
        assertFalse(buildOptional.isPresent());

        builder = builder.currencyPair(CurrencyPair.create(fromCurrency, toCurrency));
        assertNotNull(builder);
        buildOptional = builder.build();
        assertNotNull(buildOptional);
        assertFalse(buildOptional.isPresent());

        builder = builder.quotedRate(fromCurrency, toCurrency, conversionRate);
        assertNotNull(builder);
        buildOptional = builder.build();
        assertNotNull(buildOptional);
        assertTrue(buildOptional.isPresent());

        CurrencyPairRate currencyPairRate = buildOptional.get();
        assertNotNull(currencyPairRate);
        assertEquals(CurrencyPair.create(fromCurrency, toCurrency), currencyPairRate.getCurrencyPair());
        assertEquals(conversionRate.setScale(6, BigDecimal.ROUND_HALF_EVEN), currencyPairRate.getConversionRate(CurrencyPair.Direction.INVERSED));
        assertEquals(new BigDecimal(1.0).setScale(6, BigDecimal.ROUND_HALF_EVEN).divide(conversionRate, BigDecimal.ROUND_HALF_EVEN),
                currencyPairRate.getConversionRate(CurrencyPair.Direction.NORMAL));
    }
}
