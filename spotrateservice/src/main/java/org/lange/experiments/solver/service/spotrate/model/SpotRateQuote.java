package org.lange.experiments.solver.service.spotrate.model;

import org.joda.time.DateTime;
import org.lange.experiments.solver.models.Currency;
import org.lange.experiments.solver.models.CurrencyPair;
import org.lange.experiments.solver.models.CurrencyPairRate;
import org.lange.experiments.solver.models.ModelBuilder;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by lange on 26/11/16.
 */
public class SpotRateQuote {

    private DateTime validFrom;
    private DateTime validUntil;
    private CurrencyPairRate currencyPairRate;

    private SpotRateQuote() {
        super();
    }

    public CurrencyPair getCurrencyPair() {
        return Optional.ofNullable(currencyPairRate).map(CurrencyPairRate::getCurrencyPair).orElse(null);
    }

    public Optional<BigDecimal> getConversionRate(Currency fromCurrency, Currency toCurrency) {
        return getCurrencyPair().getRelativeDirection(fromCurrency, toCurrency).map(currencyPairRate::getConversionRate);
    }

    private void setCurrencyPairRate(CurrencyPairRate currencyPairRate) {
        this.currencyPairRate = currencyPairRate;
    }

    private CurrencyPairRate getCurrencyPairRate() {
        return currencyPairRate;
    }

    public DateTime getValidFrom() {
        return validFrom;
    }

    private void setValidFrom(DateTime validFrom) {
        this.validFrom = validFrom;
    }

    public DateTime getValidUntil() {
        return validUntil;
    }

    public boolean isValid(DateTime dateTime) {
        return validFrom.isBefore(dateTime) && validUntil.isAfter(dateTime);
    }

    private void setValidUntil(DateTime validUntil) {
        this.validUntil = validUntil;
    }

    public static class Builder implements ModelBuilder<SpotRateQuote> {
        SpotRateQuote object = new SpotRateQuote();

        private Builder() {
            super();
        }

        @Override
        public Stream<Function<SpotRateQuote, ?>> getFieldExtractors() {
            return Stream.of(SpotRateQuote::getCurrencyPairRate, SpotRateQuote::getValidFrom, SpotRateQuote::getValidUntil);
        }

        @Override
        public SpotRateQuote getBaseObject() {
            return object;
        }

        public static Builder create() {
            return new Builder();
        }

        public Builder rate(Currency fromCurrency, Currency toCurrency, BigDecimal rate) {
            Optional.ofNullable(rate)
                    .map(rt -> Optional.ofNullable(fromCurrency)
                            .map(f -> Optional.ofNullable(toCurrency)
                                    .map(t -> CurrencyPairRate.Builder.create().quotedRate(fromCurrency, toCurrency, rate).build().orElse(null))
                                    .orElse(null))
                            .orElse(null))
                    .ifPresent(object::setCurrencyPairRate);
            return this;
        }

        public Builder rate(String fromCurrency, String toCurrency, BigDecimal rate) {
            Optional.ofNullable(rate)
                    .map(rt -> Optional.ofNullable(fromCurrency).map(Currency::resolve).map(optional -> optional.isPresent() ? optional.get() : null)
                            .map(f -> Optional.ofNullable(toCurrency).map(Currency::resolve).map(optional -> optional.isPresent() ? optional.get() : null)
                                    .map(t -> CurrencyPairRate.Builder.create().quotedRate(f, t, rate).build().orElse(null))
                                    .orElse(null))
                            .orElse(null))
                    .ifPresent(object::setCurrencyPairRate);
            return this;
        }

        public Builder validFrom(DateTime dateTime) {
            Optional.ofNullable(dateTime).ifPresent(object::setValidFrom);
            return this;
        }

        public Builder validUntil(DateTime dateTime) {
            Optional.ofNullable(dateTime).ifPresent(object::setValidUntil);
            return this;
        }
    }
}
