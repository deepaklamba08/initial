package com.maven.org.common.api.model;

public interface Row {

    public String getString(int index);

    public String getString(String field);

    public Object get(String field);

    public Object get(int index);

}
