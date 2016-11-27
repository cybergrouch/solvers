package org.lange.experiments.solver.service.spotrate.utils;

import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by lange on 27/11/16.
 */
public final class FileUtils {

    private FileUtils() {
        super();
    }

    public static final Function<String, String> resourceNameToPath = name -> String.format("%s/src/%s", System.getProperty("user.dir"), name);

    public static final Function<String, Reader> readerFromResource = name -> {
        try {
            return new FileReader(name);
        } catch (FileNotFoundException e) {
            return null;
        }
    };

    public static final Function<Reader, String> toString = reader -> Optional.ofNullable(reader).map(rd -> {
            try {
                return IOUtils.toString(reader);
            } catch (IOException e) {
                return null;
            }
        }).orElse("");
}
