package com.maven.org.common.api.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SchemaRow implements Row {

    private List<String> columns;
    private Map<String, Object> data;

    public SchemaRow(Map<String, Object> data) {
        this.data = data;
        this.columns = new ArrayList<>(data.keySet());
    }

    public Map<String, Object> getData() {
        return data;
    }

    public List<String> getColumns() {
        return columns;
    }

    @Override
    public String getString(int index) {
        return null;
    }

    @Override
    public String getString(String field) {
        Object value = this.get(field);
        return value != null ? String.valueOf(value) : null;
    }

    @Override
    public Object get(String field) {
        if (hasField(field)) {
            return this.data != null ? this.data.get(field) : null;
        } else {
            throw new IllegalStateException("Field "+field+" not present in schema.");
        }
    }

    @Override
    public Object get(int index) {
        return null;
    }

    @Override
    public String toString() {
        return "| " + data + " |";
    }

    @Override
    public boolean hasField(String field) {
        return this.data.keySet().contains(field);
    }
}
