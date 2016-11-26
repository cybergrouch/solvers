package org.lange.experiments.solver.cocosolver.currencytransfer.models;

import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.lange.experiments.solver.cocosolver.currencytransfer.models.RequestCsvParser.parse;

/**
 * Created by lange on 26/11/16.
 */
public class RequestCsvParserTest {

    @Test
    public void testAccessResource() throws IOException {
        String userDirectory = System.getProperty("user.dir");
        String resourcePath = String.format("%s/%s/%s", userDirectory, "src/test/resources/csv", "1_requests_examples.csv.txt");
        Reader in = new FileReader(resourcePath);
        List<TransferRequest> parse = parse(in);
        assertNotNull(parse);
        assertEquals(4, parse.size());
    }
}
