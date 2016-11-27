package org.lange.experiments.solver.models;

import org.apache.commons.lang3.tuple.Pair;
import org.paukov.combinatorics3.Generator;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by lange on 26/11/16.
 */
public class CurrencyPairUtil {

    public static final Comparator<? super Currency> CURRENCY_COMPARATOR = (cur1, cur2) -> cur1.name().compareTo(cur2.name());

    public static final Function<List<Currency>, List<Currency>> NORMALISE_LIST = list -> {
        list.sort(CURRENCY_COMPARATOR);
        return list;
    };

    public static final Function<List<Currency>, Pair<Currency, Currency>> LIST_TO_PAIR = listPair -> Pair.of(listPair.get(0), listPair.get(1));

    public static final Function<Pair<Currency, Currency>, List<Currency>> PAIR_TO_LIST = pair -> Stream.of(pair.getLeft(), pair.getRight()).collect(Collectors.toList());

    public static final Function<Pair<Currency, Currency>, Pair<Currency, Currency>> NORMALISE_PAIR = pair -> Optional.ofNullable(pair).map(LIST_TO_PAIR.compose(NORMALISE_LIST).compose(PAIR_TO_LIST)).orElse(null);

    public static List<Pair<Currency, Currency>> generate(Currency[] currencies) {
        return Generator.combination(currencies)
                .simple(2)
                .stream()
                .map(LIST_TO_PAIR.compose(NORMALISE_LIST))
                .collect(Collectors.toList());
    }


}
