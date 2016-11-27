package org.lange.experiments.solver.service.spotrate;

import org.json.JSONObject;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Optional;
import java.util.concurrent.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by lange on 26/11/16.
 */
public class DataExtractorModelModelTest {
    @Test
    public void testBuilder() throws FileNotFoundException {
        DataExtractorModel.Builder builder = DataExtractorModel.Builder.create(JSONObject.class);
        assertNotNull(builder);

        Optional<DataExtractorModel> buildOptional = builder.build();
        assertNotNull(buildOptional);
        assertFalse(buildOptional.isPresent());

        String userDirectory = System.getProperty("user.dir");
        String resourcePath = String.format("%s/%s/%s", userDirectory, "src/test/resources/json", "yahoo_finance.json");
        Reader in = new FileReader(resourcePath);
        assertNotNull(in);

        builder = builder.reader(in);
        assertNotNull(builder);
        buildOptional = builder.build();
        assertNotNull(buildOptional);
        assertTrue(buildOptional.isPresent());

        DataExtractorModel dataExtractorModel = buildOptional.orElse(null);
        assertNotNull(dataExtractorModel);
    }

    @Test
    public void testExtractor() throws FileNotFoundException {
        String userDirectory = System.getProperty("user.dir");
        String resourcePath = String.format("%s/%s/%s", userDirectory, "src/test/resources/json", "yahoo_finance.json");
        Reader in = new FileReader(resourcePath);
        assertNotNull(in);

        DataExtractorModel.Builder builder = DataExtractorModel.Builder.create(JSONObject.class).reader(in);
        assertNotNull(builder);

        Optional<DataExtractorModel> buildOptional = builder.build();
        assertNotNull(buildOptional);
        assertTrue(buildOptional.isPresent());

        DataExtractorModel dataExtractorModel = buildOptional.orElse(null);
        assertNotNull(dataExtractorModel);

        Callable<JSONObject> extractor = dataExtractorModel.getExtractor(new JSONObject());

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        ExecutorCompletionService<JSONObject> completionService = new ExecutorCompletionService<JSONObject>(executorService);
        Future<JSONObject> submit = completionService.submit(extractor);


    }
}
