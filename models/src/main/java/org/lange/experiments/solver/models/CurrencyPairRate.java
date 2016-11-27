package org.lange.experiments.solver.models;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by lange on 27/11/16.
 */
public class CurrencyPairRate {

    private CurrencyPair currencyPair;
    private InversibleRate currencyPairRate;

    private CurrencyPairRate() {
        super();
    }

    public BigDecimal getConversionRate(CurrencyPair.Direction direction) {
        if (CurrencyPair.Direction.NORMAL.equals(direction)) {
            return currencyPairRate.getRate();
        }
        return currencyPairRate.getInversedRate().getRate();
    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    private void setCurrencyPair(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
    }

    private InversibleRate getCurrencyPairRate() {
        return currencyPairRate;
    }

    private void setCurrencyPairRate(InversibleRate currencyPairRate) {
        this.currencyPairRate = currencyPairRate;
    }

    public static class Builder implements ModelBuilder<CurrencyPairRate> {
        private CurrencyPairRate object = new CurrencyPairRate();

        private Builder() {
            super();
        }

        @Override
        public Stream<Function<CurrencyPairRate, ?>> getFieldExtractors() {
            return Stream.of(CurrencyPairRate::getCurrencyPair, CurrencyPairRate::getCurrencyPairRate);
        }

        @Override
        public CurrencyPairRate getBaseObject() {
            return object;
        }

        public static Builder create() {
            return new Builder();
        }

        public Builder quotedRate(Currency fromCurrency, Currency toCurrency, BigDecimal conversionRate) {
            Optional<CurrencyPair> currencyPair = Optional.ofNullable(conversionRate)
                    .map(rt -> Optional.ofNullable(fromCurrency)
                            .map(f -> Optional.ofNullable(toCurrency)
                                    .map(t -> CurrencyPair.create(fromCurrency, toCurrency))
                                    .orElse(null))
                            .orElse(null));

            currencyPair.ifPresent(object::setCurrencyPair);
            currencyPair.map(pair -> {
                InversibleRate inversibleRate = InversibleRate.Builder.create().rate(conversionRate).build().get();
                if (CurrencyPair.Direction.NORMAL.equals(pair.getRelativeDirection(fromCurrency, toCurrency))) {
                    return inversibleRate;
                }
                return inversibleRate.getInversedRate();
            }).ifPresent(object::setCurrencyPairRate);
            return this;
        }
    }
}
