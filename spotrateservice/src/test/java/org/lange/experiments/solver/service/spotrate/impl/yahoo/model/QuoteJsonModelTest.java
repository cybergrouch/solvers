package org.lange.experiments.solver.service.spotrate.impl.model.yahoo;

import org.junit.Test;
import org.lange.experiments.solver.service.spotrate.utils.FileUtils;
import org.lange.experiments.solver.service.spotrate.utils.JsonUtils;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by lange on 27/11/16.
 */
public class QuoteJsonModelTest {

    @Test
    public void testDeserialisation() {
        Optional<QuoteJsonModel> quoteJsonModelOptional = Optional.of("test/resources/json/yahoo/quote.json")
                .map(FileUtils.toString.compose(FileUtils.readerFromResource.compose(FileUtils.resourceNameToPath)))
                .map(JsonUtils.getReaderForClass(QuoteJsonModel.class));
        assertNotNull(quoteJsonModelOptional);

        QuoteJsonModel quoteJsonModel = quoteJsonModelOptional.orElse(null);
        assertNotNull(quoteJsonModel);
        assertEquals("USD", quoteJsonModel.getFromCurrency());
        assertEquals("PHP", quoteJsonModel.getToCurrency());
        assertEquals("49.830002", quoteJsonModel.getPrice().toString());
        assertEquals("2016-11-25T23:54:40.000Z", quoteJsonModel.getQuoteTime().toString());

    }

}
