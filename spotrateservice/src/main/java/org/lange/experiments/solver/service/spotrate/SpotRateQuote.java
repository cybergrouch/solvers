package org.lange.experiments.solver.service.spotrate;

import org.apache.commons.lang3.tuple.Pair;
import org.lange.experiments.solver.models.Currency;
import org.lange.experiments.solver.models.CurrencyPair;
import org.lange.experiments.solver.models.ModelBuilder;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by lange on 26/11/16.
 */
public class SpotRateQuote {

    private CurrencyPair currencyPair;
    private BigDecimal normalisedRate;
    private BigDecimal inverseRate;

    private SpotRateQuote() {
        super();
    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    private void setCurrencyPair(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
    }

    public Optional<BigDecimal> getConversionRate(Currency fromCurrency, Currency toCurrency) {
        if (currencyPair.getLeft().equals(fromCurrency) && currencyPair.getRight().equals(toCurrency)) {
            return Optional.of(normalisedRate);
        } else if (currencyPair.getLeft().equals(toCurrency) && currencyPair.getRight().equals(fromCurrency)) {
            return Optional.of(inverseRate);
        } else {
            return Optional.empty();
        }
    }

    private void setConversionRate(Currency fromCurrency, Currency toCurrency, BigDecimal rate) {
        CurrencyPair currencyPair = CurrencyPair.create(fromCurrency, toCurrency);
        this.setCurrencyPair(currencyPair);

        BigDecimal inversed = new BigDecimal(1.0).setScale(6, BigDecimal.ROUND_HALF_EVEN).divide(rate, BigDecimal.ROUND_HALF_EVEN);
        if (!currencyPair.getLeft().equals(fromCurrency) || !currencyPair.getRight().equals(toCurrency)) {
            this.inverseRate = rate;
            this.normalisedRate = inversed;
        } else {
            this.normalisedRate = rate;
            this.inverseRate = inversed;
        }
    }

    public static class Builder implements ModelBuilder<SpotRateQuote> {
        SpotRateQuote object = new SpotRateQuote();

        private Builder() {
            super();
        }

        @Override
        public Stream<Function<SpotRateQuote, ?>> getFieldExtractors() {
            return Stream.of(SpotRateQuote::toString, SpotRateQuote::getCurrencyPair);
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
                    .map(rt -> rt.setScale(6, BigDecimal.ROUND_HALF_EVEN))
                    .map(rt ->
                            Optional.ofNullable(fromCurrency)
                                    .map(f ->
                                            Optional.ofNullable(toCurrency)
                                                .map(t -> Pair.of(f, t)).get())
                                    .map(p -> Pair.of(p, rt)).get())
                    .ifPresent(oP -> {
                        BigDecimal conversionRate = oP.getRight();
                        Pair<Currency, Currency> conversionDirection = oP.getLeft();
                        object.setConversionRate(conversionDirection.getLeft(), conversionDirection.getRight(), conversionRate);
                    });
            return this;
        }
    }
}
