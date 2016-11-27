package org.lange.experiments.solver.service.spotrate;

import org.lange.experiments.solver.models.ModelBuilder;

import java.io.Reader;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by lange on 26/11/16.
 */
public class DataExtractorModel<T> {

    private Supplier<Reader> dataReader;

    private DataExtractorModel(Class<T> clazz) {
        super();
    }

    public Supplier<Reader> getDataReader() {
        return dataReader;
    }

    private void setDataReader(Supplier<Reader> dataReader) {
        this.dataReader = dataReader;
    }

    public Callable<T> getExtractor(T defaultValue) {
        return new Callable<T>() {
            @Override
            public T call() throws Exception {
                return defaultValue;
//                Optional.ofNullable(dataReader).map(Supplier::get);
            }
        };
    }

    public static class Builder<T> implements ModelBuilder<DataExtractorModel> {
        DataExtractorModel<T> object;

        private Builder(Class<T> clazz) {
            super();
            object = new DataExtractorModel<>(clazz);
        }

        public Builder reader(Reader reader) {
            Optional.ofNullable(reader).ifPresent(in -> {
                object.setDataReader(() -> in);
            });
            return this;
        }

        public Stream<Function<DataExtractorModel, ?>> getFieldExtractors() {
            return Stream.of(DataExtractorModel::getDataReader);
        }

        public DataExtractorModel getBaseObject() {
            return object;
        }

        public static <T> Builder<T> create(Class<T> clazz) {
            return new Builder<>(clazz);
        }
    }
}
