package org.lange.experiments.solver.cocosolver.currencytransfer.models;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by lange on 25/11/16.
 */
public interface ModelBuilder<T> {

    Stream<Function<T, ?>> getFieldExtractors();

    T getBaseObject();

    default Optional<T> build() {
        Stream<Function<T, ?>> stream = getFieldExtractors();
        boolean completeInput =
                stream
                        .map(function -> Optional.ofNullable(function.apply(getBaseObject())))
                        .map(Optional::isPresent)
                        .reduce(Boolean::logicalAnd)
                        .orElse(Boolean.FALSE);
        if (completeInput) {
            return Optional.of(getBaseObject());
        }
        return Optional.empty();
    }
}
