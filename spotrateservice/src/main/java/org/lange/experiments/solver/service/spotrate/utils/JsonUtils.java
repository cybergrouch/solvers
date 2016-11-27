package org.lange.experiments.solver.service.spotrate.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by lange on 27/11/16.
 */
public class JsonUtils {

    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static final <T> Function<String, T> getReaderForClass(Class<T> clazz) {
        return jsonString -> {
            try {
                T value = objectMapper.readValue(jsonString, clazz);
                return value;
            } catch (IOException e) {
                return null;
            }
        };
    }
}
