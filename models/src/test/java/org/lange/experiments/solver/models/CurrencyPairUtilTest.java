package org.lange.experiments.solver.models;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by lange on 26/11/16.
 */
public class CurrencyPairUtilTest {

    @Test
    public void testCalculateCurrencyPairs() {
        List<Pair<Currency, Currency>> currencyPairs = CurrencyPairUtil.generate(Currency.values());
        assertNotNull(currencyPairs);

        currencyPairs.forEach(pair -> {
            assertTrue(pair.getLeft().name().compareTo(pair.getRight().name()) <= 0);
        });
    }

    @Test
    public void testNormalisePair() {
        Pair<Currency, Currency> pair1 = Pair.of(Currency.EUR, Currency.AUD);
        Pair<Currency, Currency> pair2 = CurrencyPairUtil.NORMALISE_PAIR.apply(pair1);
        assertNotNull(pair2);
        Assert.assertEquals(Currency.AUD, pair2.getLeft());
        Assert.assertEquals(Currency.EUR, pair2.getRight());
    }
}
