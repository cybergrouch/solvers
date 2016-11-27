package org.lange.experiments.solver.cocosolver.currencytransfer.models;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.lange.experiments.solver.models.*;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by lange on 25/11/16.
 */
public class TransferRequestTest {

    @Test
    public void testBuilder() {
        TransferRequest.Builder builder = TransferRequest.Builder.create();
        assertNotNull(builder);
        assertTrue(ModelBuilder.class.isAssignableFrom(builder.getClass()));
    }

    @Test
    public void testValidProperties() {
        TransferRequest.Builder builder =
                TransferRequest.Builder
                        .create()
                        .monetaryAmount(
                                MonetaryAmount.Builder
                                        .create()
                                        .currency(Currency.USD)
                                        .amount(10000L).build().orElse(null))
                        .targetCurrency(Currency.AUD)
                        .validFrom(DateTimeBuilder.create().year(2016).month(1).date(15).hour(10).minute(45).build().orElse(null))
                        .validUntil(DateTimeBuilder.create().year(2016).month(1).date(16).hour(10).minute(45).build().orElse(null))
                        .requestorId(12345L)
                        .requestId(192838L);
        assertNotNull(builder);

        Optional<TransferRequest> transferRequestOptional = builder.build();
        assertNotNull(transferRequestOptional);
        assertTrue(transferRequestOptional.isPresent());

        TransferRequest transferRequest = transferRequestOptional.get();
        assertNotNull(transferRequest);

        MonetaryAmount monetaryAmount = transferRequest.getAmount();
        assertNotNull(monetaryAmount);
        assertEquals(Currency.USD, monetaryAmount.getCurrency());
        assertEquals((Long) 10000L, monetaryAmount.getAmount());

        transferRequest.setAmount(MonetaryAmount.Builder
                .create()
                .currency(Currency.PHP)
                .amount(20000L).build().orElse(null));

        MonetaryAmount monetaryAmount2 = transferRequest.getAmount();
        assertNotNull(monetaryAmount2);
        assertEquals(Currency.PHP, monetaryAmount2.getCurrency());
        assertEquals((Long) 20000L, monetaryAmount2.getAmount());

        Currency targetCurrency = transferRequest.getTargetCurrency();
        assertNotNull(targetCurrency);
        assertEquals(Currency.AUD, targetCurrency);

        transferRequest.setTargetCurrency(Currency.SGD);
        assertEquals(Currency.SGD, transferRequest.getTargetCurrency());

        DateTime validFrom = transferRequest.getValidFrom();
        assertNotNull(validFrom);
        DateTime baseValidFrom = new DateTime().withZone(DateTimeZone.UTC).withYear(2016).withMonthOfYear(1).withDayOfMonth(15).withHourOfDay(2).withMinuteOfHour(45).withSecondOfMinute(0).withMillisOfSecond(0);
        assertTrue(baseValidFrom.isEqual(validFrom));

        transferRequest.setValidFrom(null);
        assertNull(transferRequest.getValidFrom());

        DateTime validUntil = transferRequest.getValidUntil();
        assertNotNull(validUntil);
        DateTime baseValidUntil = new DateTime().withZone(DateTimeZone.UTC).withYear(2016).withMonthOfYear(1).withDayOfMonth(16).withHourOfDay(2).withMinuteOfHour(45).withSecondOfMinute(0).withMillisOfSecond(0);
        assertTrue(baseValidUntil.isEqual(validUntil));

        transferRequest.setValidUntil(null);
        assertNull(transferRequest.getValidUntil());

        Long requestorId = transferRequest.getRequestorId();
        assertNotNull(requestorId);
        assertEquals((Long) 12345L, requestorId);

        transferRequest.setRequestorId(54321L);
        assertEquals((Long) 54321L, transferRequest.getRequestorId());

        Long requestId = transferRequest.getRequestId();
        assertNotNull(requestId);
        assertEquals((Long) 192838L, requestId);

        transferRequest.setRequestId(6789L);
        assertEquals((Long) 6789L, transferRequest.getRequestId());

        CurrencyPair expected = CurrencyPair.create(Currency.SGD, Currency.PHP);
        assertEquals(expected, transferRequest.getCurrencyPair());

    }

    @Test
    public void testInvalidProperties() {
        TransferRequest.Builder builder =
                TransferRequest.Builder
                        .create()
                        .monetaryAmount(
                                MonetaryAmount.Builder
                                        .create()
                                        .currency(Currency.USD)
                                        .amount(10000L).build().orElse(null));
        assertNotNull(builder);

        Optional<TransferRequest> transferRequestOptional = builder.build();
        assertNotNull(transferRequestOptional);
        assertFalse(transferRequestOptional.isPresent());


        builder =
                TransferRequest.Builder
                        .create()
                        .monetaryAmount(
                                MonetaryAmount.Builder
                                        .create()
                                        .currency(Currency.USD)
                                        .amount(10000L).build().orElse(null))
                        .targetCurrency(Currency.AUD);
        assertNotNull(builder);

        transferRequestOptional = builder.build();
        assertNotNull(transferRequestOptional);
        assertFalse(transferRequestOptional.isPresent());

        builder =
                TransferRequest.Builder
                        .create()
                        .monetaryAmount(
                                MonetaryAmount.Builder
                                        .create()
                                        .currency(Currency.USD)
                                        .amount(10000L).build().orElse(null))
                        .targetCurrency(Currency.AUD)
                        .validFrom(DateTimeBuilder.create().year(2016).month(1).date(15).hour(10).minute(45).build().orElse(null));
        assertNotNull(builder);

        transferRequestOptional = builder.build();
        assertNotNull(transferRequestOptional);
        assertFalse(transferRequestOptional.isPresent());

        builder =
                TransferRequest.Builder
                        .create()
                        .monetaryAmount(
                                MonetaryAmount.Builder
                                        .create()
                                        .currency(Currency.USD)
                                        .amount(10000L).build().orElse(null))
                        .targetCurrency(Currency.AUD)
                        .validFrom(DateTimeBuilder.create().year(2016).month(1).date(15).hour(10).minute(45).build().orElse(null))
                        .validUntil(DateTimeBuilder.create().year(2016).month(1).date(16).hour(10).minute(45).build().orElse(null));
        assertNotNull(builder);

        transferRequestOptional = builder.build();
        assertNotNull(transferRequestOptional);
        assertFalse(transferRequestOptional.isPresent());

        builder =
                TransferRequest.Builder
                        .create()
                        .monetaryAmount(
                                MonetaryAmount.Builder
                                        .create()
                                        .currency(Currency.USD)
                                        .amount(10000L).build().orElse(null))
                        .targetCurrency(Currency.AUD)
                        .validFrom(DateTimeBuilder.create().year(2016).month(1).date(15).hour(10).minute(45).build().orElse(null))
                        .validUntil(DateTimeBuilder.create().year(2016).month(1).date(16).hour(10).minute(45).build().orElse(null))
                        .requestorId(12345L);;
        assertNotNull(builder);

        transferRequestOptional = builder.build();
        assertNotNull(transferRequestOptional);
        assertFalse(transferRequestOptional.isPresent());


        builder =
                TransferRequest.Builder
                        .create()
                        .monetaryAmount(
                                MonetaryAmount.Builder
                                        .create()
                                        .currency(Currency.USD)
                                        .amount(10000L).build().orElse(null))
                        .targetCurrency(Currency.AUD)
                        .validFrom(DateTimeBuilder.create().year(2016).month(1).date(15).hour(10).minute(45).build().orElse(null))
                        .validUntil(DateTimeBuilder.create().year(2016).month(1).date(16).hour(10).minute(45).build().orElse(null))
                        .requestorId(12345L)
                        .requestId(192838L);
        assertNotNull(builder);

        transferRequestOptional = builder.build();
        assertNotNull(transferRequestOptional);
        assertTrue(transferRequestOptional.isPresent());

        CurrencyPair expected = CurrencyPair.create(Currency.AUD, Currency.USD);
        assertEquals(expected, transferRequestOptional.get().getCurrencyPair());

        Currency sourceCurrency = transferRequestOptional.get().getSourceCurrency();
        assertEquals(Currency.USD, sourceCurrency);
    }

}
