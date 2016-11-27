package org.lange.experiments.solver.service.spotrate.impl.yahoo;

import org.junit.Test;
import org.lange.experiments.solver.service.spotrate.model.SpotRateQuote;
import rx.Observer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by lange on 28/11/16.
 */
public class YahooCurrencyDataExtractorImplTest {

    @Test
    public void testInitialisation() {
        YahooCurrencyDataExtractorImpl impl = YahooCurrencyDataExtractorImpl.Holder.INSTANCE;
        assertNotNull(impl);
    }


    @Test
    public void testExtract() {
        YahooCurrencyDataExtractorImpl impl = YahooCurrencyDataExtractorImpl.Holder.INSTANCE;
        assertNotNull(impl);

        List<Boolean> holder = new ArrayList<>();
        List<RuntimeException> errors = new ArrayList<>();
        impl.extract().subscribe(new Observer<List<SpotRateQuote>>() {
            @Override
            public void onCompleted() {
                System.out.println("Request completed.");
            }

            @Override
            public void onError(Throwable e) {
                errors.add(new RuntimeException("Error during run", e));
            }

            @Override
            public void onNext(List<SpotRateQuote> spotRateQuotes) {
                try {
                    assertNotNull(spotRateQuotes);
                    assertEquals(6, spotRateQuotes.size());
                    holder.add(true);
                } catch (RuntimeException e) {
                    errors.add(e);
                }
            }
        });


        while (holder.isEmpty() && errors.isEmpty()) {
            Thread.yield();
        }

        if (!errors.isEmpty()) {
            throw errors.get(0);
        }
    }
}
