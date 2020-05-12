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

    public List<String> getColumns() {
        return columns;
    }

    @Override
    public String getString(int index) {
        return null;
    }

    @Override
    public String getString(String field) {
        return null;
    }

    @Override
    public Object get(String field) {
        return null;
    }

    @Override
    public Object get(int index) {
        return null;
    }
}
