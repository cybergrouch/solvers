package org.lange.experiments.solver.models;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by lange on 25/11/16.
 */
public enum Currency {
    EUR, JPY(0), JOD(3), SGD, AUD, PHP, USD;

    private int minorUnits;

    Currency() {
        this(2);
    }

    Currency(int minorUnits) {
        this.minorUnits = minorUnits;
    }

    public int getMinorUnits() {
        return this.minorUnits;
    }

    public static Optional<Currency> resolve(String currencyCode) {
        String inputCurrencyCode = StringUtils.isBlank(currencyCode) ? "" : currencyCode.toUpperCase();
        return Arrays.stream(values()).filter(currency -> currency.name().toUpperCase().equals(inputCurrencyCode)).findFirst();
    }

    @Override
    public String toString() {
        return this.name();
    }
}
