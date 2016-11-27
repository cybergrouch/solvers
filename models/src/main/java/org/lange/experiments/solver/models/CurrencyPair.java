package org.lange.experiments.solver.models;

import com.codepoetics.protonpack.StreamUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by lange on 26/11/16.
 */
public class CurrencyPair {

    public enum Direction {
        NORMAL,
        INVERSED;
    }

    private Currency left;
    private Currency right;

    private static final Stream<Function<CurrencyPair, ?>> getFieldExtractors() {
        return Stream.of(CurrencyPair::getLeft, CurrencyPair::getRight);
    }

    public Currency getLeft() {
        return left;
    }

    private void setLeft(Currency left) {
        this.left = left;
    }

    public Currency getRight() {
        return right;
    }

    private void setRight(Currency right) {
        this.right = right;
    }

    public Optional<Direction> getRelativeDirection(Currency fromCurrency, Currency toCurrency) {
        return Optional.ofNullable(fromCurrency).map(f -> Optional.ofNullable(toCurrency)).map(t -> {
            if (fromCurrency == left && toCurrency == right) {
                return Direction.NORMAL;
            } else if (fromCurrency == right && toCurrency == left) {
                return Direction.INVERSED;
            }
            return null;
        });
    }

    public Optional<CurrencyPairRate> createRate(Currency fromCurrency, Currency toCurrency, BigDecimal conversionRate) {
        return CurrencyPairRate.Builder.create().quotedRate(fromCurrency, toCurrency, conversionRate).build();
    }

    @Override
    public String toString() {
        return String.format("%s-%s", getLeft().name(), getRight().name());
    }

    @Override
    public int hashCode() {
        Optional<String> concat = StreamUtils.zip(getFieldExtractors(), Stream.generate(() -> this), (f, i) -> f.apply(i).toString()).reduce(String::concat);
        return Objects.hashCode(concat.orElse(""));
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof CurrencyPair) {
            final CurrencyPair other = (CurrencyPair) obj;

            Stream<?> thisFieldValues = StreamUtils.zip(
                    getFieldExtractors(),
                    Stream.generate(() -> this),
                    (f, i) -> f.apply(i));
            Stream<?> otherFieldValues = StreamUtils.zip(
                    getFieldExtractors(),
                    Stream.generate(() -> other),
                    (f, i) -> f.apply(i));
            return StreamUtils.zip(
                    thisFieldValues,
                    otherFieldValues,
                    (t, o) -> t.equals(o))
                    .reduce(Boolean::logicalAnd)
                    .orElse(Boolean.FALSE);
        } else {
            return false;
        }
    }

    public static CurrencyPair create(Currency firstCurrency, Currency secondCurrency) {
        return Builder.create().currencies(firstCurrency, secondCurrency).build().orElse(null);
    }

    public static class Builder implements ModelBuilder<CurrencyPair> {
        private CurrencyPair object = new CurrencyPair();

        private Builder() {
            super();
        }

        public static CurrencyPair.Builder create() {
            return new CurrencyPair.Builder();
        }

        public Builder currencies(Currency firstCurrency, Currency secondCurrency) {
            Optional.of(Pair.of(firstCurrency, secondCurrency))
                    .map(CurrencyPairUtil.NORMALISE_PAIR)
                    .ifPresent(pair -> {
                        object.setLeft(pair.getLeft());
                        object.setRight(pair.getRight());
                    });
            return this;
        }

        @Override
        public Stream<Function<CurrencyPair, ?>> getFieldExtractors() {
            return CurrencyPair.getFieldExtractors();
        }

        @Override
        public CurrencyPair getBaseObject() {
            return object;
        }
    }
}
