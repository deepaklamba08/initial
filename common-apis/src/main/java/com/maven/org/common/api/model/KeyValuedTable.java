package com.maven.org.common.api.model;

import com.maven.org.common.api.model.Dataset;

public interface KeyValuedTable<K> {

    public Dataset aggregate(String... aggColumns);


}
