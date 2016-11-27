package org.lange.experiments.solver.service.spotrate;

import org.junit.Test;
import org.lange.experiments.solver.models.Currency;
import org.lange.experiments.solver.models.CurrencyPair;
import org.lange.experiments.solver.models.ModelBuilder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by lange on 26/11/16.
 */
public class SpotRateQuoteTest {

    @Test
    public void testBuilder() {
        SpotRateQuote.Builder builder = SpotRateQuote.Builder.create();
        assertNotNull(builder);
        assertTrue(ModelBuilder.class.isAssignableFrom(builder.getClass()));

        Optional<SpotRateQuote> buildOptional = builder.build();
        assertNotNull(buildOptional);
        assertFalse(buildOptional.isPresent());
    }

    @Test
    public void testValidProperties() {
        BigDecimal rate = new BigDecimal(49.830002);

        SpotRateQuote.Builder builder = SpotRateQuote.Builder.create();
        assertNotNull(builder);
        Optional<SpotRateQuote> buildOptional = builder.rate(Currency.USD, Currency.PHP, rate).build();
        assertNotNull(buildOptional);
        assertTrue(buildOptional.isPresent());

        SpotRateQuote spotRateQuote = buildOptional.get();
        assertNotNull(spotRateQuote);

        CurrencyPair currencyPair = spotRateQuote.getCurrencyPair();
        assertEquals(CurrencyPair.create(Currency.USD, Currency.PHP), currencyPair);
        assertEquals(CurrencyPair.create(Currency.PHP, Currency.USD), currencyPair);

        assertEquals(rate.setScale(6, BigDecimal.ROUND_HALF_EVEN), spotRateQuote.getConversionRate(Currency.USD, Currency.PHP).orElse(BigDecimal.ZERO));
        assertEquals(new BigDecimal(1.0).setScale(6, BigDecimal.ROUND_HALF_EVEN).divide(rate, BigDecimal.ROUND_HALF_EVEN), spotRateQuote.getConversionRate(Currency.PHP, Currency.USD).orElse(BigDecimal.ZERO));
    }

    @Test
    public void testInvalidProperties() {
        SpotRateQuote.Builder builder = SpotRateQuote.Builder.create();
        assertNotNull(builder);
        Optional<SpotRateQuote> buildOptional = builder.build();
        assertNotNull(buildOptional);
        assertFalse(buildOptional.isPresent());

        BigDecimal rate = new BigDecimal(49.830002);
        builder = builder.rate(Currency.USD, Currency.PHP, rate);
        assertNotNull(builder);
        buildOptional = builder.build();
        assertNotNull(buildOptional);
        assertTrue(buildOptional.isPresent());

        SpotRateQuote spotRateQuote = buildOptional.orElse(null);

        assertEquals(rate.setScale(6, BigDecimal.ROUND_HALF_EVEN), spotRateQuote.getConversionRate(Currency.USD, Currency.PHP).orElse(BigDecimal.ZERO));
        assertEquals(new BigDecimal(1.0).setScale(6, BigDecimal.ROUND_HALF_EVEN).divide(rate, BigDecimal.ROUND_HALF_EVEN), spotRateQuote.getConversionRate(Currency.PHP, Currency.USD).orElse(BigDecimal.ZERO));

        CurrencyPair currencyPair = CurrencyPair.create(Currency.USD, Currency.PHP);
        assertEquals(currencyPair, spotRateQuote.getCurrencyPair());
    }
}
