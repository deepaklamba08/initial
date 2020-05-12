package com.maven.org.common.api.model;

public class ProjectionColumn implements Column {
    private String name;

    private String alias;

    public ProjectionColumn(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }

    public ProjectionColumn(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getAlias() {
        return null;
    }
}
