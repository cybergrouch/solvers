package org.lange.experiments.solver.models;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by lange on 27/11/16.
 */
public class InversibleRate {

    public static final Function<BigDecimal, BigDecimal> SCALE = number -> number.setScale(6, BigDecimal.ROUND_HALF_EVEN);
    public static final Function<BigDecimal, BigDecimal> INVERSE = normal -> Optional.of(new BigDecimal(1.0)).map(SCALE).orElse(BigDecimal.ZERO).divide(normal, BigDecimal.ROUND_HALF_EVEN);

    private BigDecimal rate;
    private boolean inversed;
    private InversibleRate inversedRate;

    private InversibleRate() {
        super();
    }

    private InversibleRate(InversibleRate inversedRate) {
        super();
        this.inversed = !inversedRate.isInversed();
        this.rate = Optional.of(inversedRate).map(InversibleRate::getRate).map(INVERSE).orElse(BigDecimal.ZERO);
        this.inversedRate = inversedRate;
    }

    public BigDecimal getRate() {
        return rate;
    }

    private void setRate(BigDecimal rate) {
        this.rate = rate;
        this.inversedRate = new InversibleRate(this);
    }

    public boolean isInversed() {
        return inversed;
    }

    public InversibleRate getInversedRate() {
        return inversedRate;
    }

    public static class Builder implements ModelBuilder<InversibleRate> {
        private InversibleRate object = new InversibleRate();

        @Override
        public Stream<Function<InversibleRate, ?>> getFieldExtractors() {
            return Stream.of(InversibleRate::getRate);
        }

        @Override
        public InversibleRate getBaseObject() {
            return object;
        }

        public static Builder create() {
            return new Builder();
        }

        public Builder rate(BigDecimal rate) {
            Optional.ofNullable(rate).map(SCALE).ifPresent(object::setRate);
            return this;
        }
    }
}
