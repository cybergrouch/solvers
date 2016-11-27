package org.lange.experiments.solver.service.spotrate;

import org.lange.experiments.solver.service.spotrate.model.SpotRateQuote;
import rx.Observable;

import java.util.List;

/**
 * Created by lange on 28/11/16.
 */
public interface CurrencyDataExtractor {
    Observable<List<SpotRateQuote>> extract();
}
