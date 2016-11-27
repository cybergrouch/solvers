package org.lange.experiments.solver.models;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by lange on 25/11/16.
 */
public class MonetaryAmount {
    private Currency currency;
    private Long amount;

    private MonetaryAmount() {
        super();
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public static class Builder implements ModelBuilder<MonetaryAmount> {
        MonetaryAmount object = new MonetaryAmount();

        private Builder() {
            super();
        }

        public static MonetaryAmount.Builder create() {
            return new MonetaryAmount.Builder();
        }

        public Builder currency(Currency currency) {
            Optional.ofNullable(currency).ifPresent(object::setCurrency);
            return this;
        }

        public Builder amount(long amount) {
            object.setAmount(amount);
            return this;
        }

        @Override
        public Stream<Function<MonetaryAmount, ?>> getFieldExtractors() {
            return Stream.of(
                    MonetaryAmount::getCurrency,
                    MonetaryAmount::getAmount);
        }

        @Override
        public MonetaryAmount getBaseObject() {
            return object;
        }
    }
}
