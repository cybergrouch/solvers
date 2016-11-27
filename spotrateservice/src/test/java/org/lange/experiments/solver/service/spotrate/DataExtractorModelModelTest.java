package org.lange.experiments.solver.service.spotrate;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.lange.experiments.solver.service.spotrate.impl.yahoo.model.QuoteJsonModel;
import org.lange.experiments.solver.service.spotrate.impl.yahoo.model.TransformUtils;
import org.lange.experiments.solver.service.spotrate.model.SpotRateQuote;
import org.lange.experiments.solver.service.spotrate.utils.FileUtils;
import org.lange.experiments.solver.service.spotrate.utils.JsonUtils;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

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

        builder = Optional.of("test/resources/json/yahoo/yahoo_finance.json")
                .map(FileUtils.readerFromResource.compose(FileUtils.resourceNameToPath))
                .map(builder::reader).orElse(null);
        assertNotNull(builder);
        buildOptional = builder.build();
        assertNotNull(buildOptional);
        assertFalse(buildOptional.isPresent());
    }

    @Test
    public void testExtractor() throws FileNotFoundException, InterruptedException, ExecutionException {
        Function<Reader, List<QuoteJsonModel>> processor = reader ->
                Optional.of(reader)
                        .map(FileUtils.toString)
                        .map(JSONObject::new)
                        .map(object -> object.getJSONObject("list"))
                        .map(object -> object.getJSONArray("resources"))
                        .map(resources -> {
                            List<QuoteJsonModel> objectList = new LinkedList<>();
                            Function<String, QuoteJsonModel> castor = JsonUtils.getReaderForClass(QuoteJsonModel.class);
                            for (int i = 0; i < resources.length(); ++i) {
                                JSONObject fields = resources.getJSONObject(i).getJSONObject("resource").getJSONObject("fields");
                                String jsonString = fields.toString();
                                QuoteJsonModel model = castor.apply(jsonString);
                                objectList.add(model);
                            }
                            return objectList;
                        }).orElse(Collections.emptyList());

        DataExtractorModel.Builder builder =
                DataExtractorModel.Builder
                        .create(List.class)
                        .reader(FileUtils.readerFromResource.compose(FileUtils.resourceNameToPath).apply("test/resources/json/yahoo/yahoo_finance.json"))
                        .processor(processor);
        assertNotNull(builder);

        Optional<DataExtractorModel> buildOptional = builder.build();
        assertNotNull(buildOptional);
        assertTrue(buildOptional.isPresent());

        DataExtractorModel dataExtractorModel = buildOptional.orElse(null);
        assertNotNull(dataExtractorModel);

        Callable<List<QuoteJsonModel>> extractor = dataExtractorModel.getExtractor(new JSONArray());

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        ExecutorCompletionService<List<QuoteJsonModel>> completionService = new ExecutorCompletionService<>(executorService);
        completionService.submit(extractor);

        Future<List<QuoteJsonModel>> take = completionService.take();
        assertNotNull(take);
        assertTrue(take.isDone());

        List<QuoteJsonModel> object = take.get();
        assertNotNull(object);

        List<SpotRateQuote> collect = object.stream().map(TransformUtils.toSpotRateQuote).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
        assertNotNull(collect);
    }
}
