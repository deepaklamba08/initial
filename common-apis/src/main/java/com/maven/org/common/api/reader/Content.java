package com.maven.org.common.api.reader;

import com.maven.org.common.api.model.Row;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Content {

    private List<String> columns;
    private List<Object> data;
    private Function<Object, Row> mapper;

    public Content(List<String> columns, List<Object> data, Function<Object, Row> mapper) {
        this.columns = columns;
        this.data = data;
        this.mapper = mapper;
    }

    public List<String> getColumns() {
        return columns;
    }

    public List<Row> getData() {
        return data.stream().map(r -> mapper.apply(r)).collect(Collectors.toList());
    }


    public static class ContentBuilder {
        private List<String> columns;
        private List<Object> data;
        private Function<Object, Row> mapper;

        public ContentBuilder columns(List<String> columns) {
            this.columns = columns;
            return this;

        }

        public ContentBuilder data(List<Object> data) {
            this.data = data;
            return this;

        }

        public ContentBuilder mapper(Function<Object, Row> mapper) {
            this.mapper = mapper;
            return this;
        }

        public Content build() {
            return new Content(columns, data, mapper);
        }

    }
}
