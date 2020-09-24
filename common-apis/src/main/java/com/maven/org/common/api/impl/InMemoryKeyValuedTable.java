package com.maven.org.common.api.impl;

import com.maven.org.common.api.model.Dataset;
import com.maven.org.common.api.model.KeyValuedTable;
import com.maven.org.common.api.model.Row;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryKeyValuedTable<K> implements KeyValuedTable<K> {

    private Map<K, List<Row>> data;

    private InMemoryKeyValuedTable(Map<K, List<Row>> data) {
        this.data = data;
    }

    @Override
    public Dataset aggregate(String... aggColumns) {
        return null;
    }


    public static class InMemoryKeyValuedTableBuilder<K> {
        private Map<K, List<Row>> data;

        public InMemoryKeyValuedTableBuilder() {
            this.data = new HashMap<>();
        }

        public InMemoryKeyValuedTableBuilder(Map<K, List<Row>> data) {
            this.data = data;
        }

        public InMemoryKeyValuedTableBuilder<K> withDataPoints(K key, List<Row> data) {
            List<Row> dataPoints = this.data.get(key);
            if (dataPoints == null) {
                dataPoints = new ArrayList<>();
                this.data.put(key, dataPoints);
            }
            dataPoints.addAll(data);
            return this;
        }

        public InMemoryKeyValuedTableBuilder<K> withDataPoint(K key, Row data) {
            List<Row> dataPoints = this.data.get(key);
            if (dataPoints == null) {
                dataPoints = new ArrayList<>();
                this.data.put(key, dataPoints);
            }
            dataPoints.add(data);
            return this;
        }

        public InMemoryKeyValuedTable<K> build() {
            return new InMemoryKeyValuedTable<>(this.data);
        }

    }

}
