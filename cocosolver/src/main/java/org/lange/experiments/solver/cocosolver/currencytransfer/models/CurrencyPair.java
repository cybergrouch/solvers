package org.lange.experiments.solver.cocosolver.currencytransfer.models;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.codepoetics.protonpack.StreamUtils.zip;

/**
 * Created by lange on 26/11/16.
 */
public class CurrencyPair {

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

    @Override
    public String toString() {
        return String.format("%s-%s", getLeft().name(), getRight().name());
    }

    @Override
    public int hashCode() {
        Optional<String> concat = zip(getFieldExtractors(), Stream.generate(() -> this), (f, i) -> f.apply(i).toString()).reduce(String::concat);
        return Objects.hashCode(concat.orElse(""));
    }

    @Override
    public boolean equals(final Object obj){
        if(obj instanceof CurrencyPair){
            final CurrencyPair other = (CurrencyPair) obj;

            Stream<?> thisFieldValues = zip(
                    getFieldExtractors(),
                    Stream.generate(() -> this),
                    (f, i) -> f.apply(i));
            Stream<?> otherFieldValues = zip(
                    getFieldExtractors(),
                    Stream.generate(() -> other),
                    (f, i) -> f.apply(i));
            return zip(
                        thisFieldValues,
                        otherFieldValues,
                        (t, o) -> t.equals(o))
                    .reduce(Boolean::logicalAnd)
                    .orElse(Boolean.FALSE);
        } else{
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
