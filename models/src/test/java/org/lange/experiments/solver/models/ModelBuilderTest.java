package org.lange.experiments.solver.models;

import org.junit.Test;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Created by lange on 25/11/16.
 */
public class ModelBuilderTest {

    static class Model {
        String field1;
        Long field2;

        private Model() {
            super();
        }

        public String getField1() {
            return field1;
        }

        public void setField1(String field1) {
            this.field1 = field1;
        }

        public Long getField2() {
            return field2;
        }

        public void setField2(Long field2) {
            this.field2 = field2;
        }

        public static class Builder implements ModelBuilder<Model> {
            Model object = new Model();

            private Builder() {
                super();
            }

            public Builder field1(String field1) {
                Optional.ofNullable(field1).ifPresent(object::setField1);
                return this;
            }

            public Builder field2(Long field2) {
                Optional.ofNullable(field2).ifPresent(object::setField2);
                return this;
            }

            public Stream<Function<Model, ?>> getFieldExtractors() {
                return Stream.of(Model::getField1, Model::getField2);
            }

            public Model getBaseObject() {
                return object;
            }

            public static Builder create() {
                return new Builder();
            }

        }
    }

    @Test
    public void testBuild() {
        Model.Builder builder = Model.Builder.create();
        assertNotNull(builder);

        Optional<Model> buildOptional = builder.build();
        assertNotNull(buildOptional);
        assertFalse(buildOptional.isPresent());

        builder = builder.field1("abc");
        buildOptional = builder.build();
        assertNotNull(buildOptional);
        assertFalse(buildOptional.isPresent());

        builder = builder.field2(123L);
        buildOptional = builder.build();
        assertNotNull(buildOptional);
        assertTrue(buildOptional.isPresent());
    }
}
