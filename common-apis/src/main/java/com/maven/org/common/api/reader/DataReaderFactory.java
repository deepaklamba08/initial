package com.maven.org.common.api.reader;

import com.maven.org.common.api.db.SchemaType;

public class DataReaderFactory {

    public static DataReader createReader(SchemaType schemaType) {
        switch (schemaType) {
            case CSV:
                return new CsvDataReader();
            default:
                throw new IllegalStateException("unsupported schema type - " + schemaType);
        }
    }

}
