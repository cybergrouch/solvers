package org.lange.experiments.solver.service.spotrate.impl.yahoo;

import org.lange.experiments.solver.service.spotrate.CurrencyDataExtractor;
import org.lange.experiments.solver.service.spotrate.model.SpotRateQuote;

import java.util.List;

/**
 * Created by lange on 28/11/16.
 */
public class YahooDataExtractorImpl implements CurrencyDataExtractor {

    private YahooDataExtractorImpl() {
        super();
    }

    @Override
    public List<SpotRateQuote> extract() {
        return null;
    }

    public static class Holder {
        public static final YahooDataExtractorImpl INSTANCE = new YahooDataExtractorImpl();
    }
}
