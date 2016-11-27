package org.lange.experiments.solver.service.spotrate.impl.yahoo;

import org.junit.Test;
import org.lange.experiments.solver.service.spotrate.model.SpotRateQuote;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by lange on 28/11/16.
 */
public class YahooDataExtractorImplTest {

    @Test
    public void testInitialisation() {
        YahooDataExtractorImpl impl = YahooDataExtractorImpl.Holder.INSTANCE;
        assertNotNull(impl);
    }


    @Test
    public void testExtract() {
        YahooDataExtractorImpl impl = YahooDataExtractorImpl.Holder.INSTANCE;
        assertNotNull(impl);

        List<SpotRateQuote> extract = impl.extract();
    }
}
