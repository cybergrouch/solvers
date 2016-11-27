package org.lange.experiments.solver.service.spotrate.model.yahoo;

import org.lange.experiments.solver.service.spotrate.model.yahoo.QuoteJsonModel;
import org.lange.experiments.solver.service.spotrate.model.SpotRateQuote;

import java.util.Optional;
import java.util.function.Function;

/**
 * Created by lange on 27/11/16.
 */
public class TransformUtils {
    public static Function<? super QuoteJsonModel, Optional<SpotRateQuote>> toSpotRateQuote =
            jsonModel ->
                    Optional.ofNullable(jsonModel)
                            .map(m -> SpotRateQuote.Builder.create())
                            .map(builder ->
                                    builder.rate(jsonModel.getFromCurrency(), jsonModel.getToCurrency(), jsonModel.getPrice())
                                            .validFrom(jsonModel.getQuoteTime())
                                            .validUntil(jsonModel.getQuoteTime().plusHours(2)))
                            .map(SpotRateQuote.Builder::build)
                            .map(optional -> optional.isPresent() ? optional.get() : null);
}
