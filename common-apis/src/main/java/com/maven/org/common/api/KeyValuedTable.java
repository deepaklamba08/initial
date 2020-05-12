package com.maven.org.common.api;

import com.maven.org.common.api.model.Column;

public interface KeyValuedTable<K> {

    public Table aggregate(Column... aggColumns);


}
