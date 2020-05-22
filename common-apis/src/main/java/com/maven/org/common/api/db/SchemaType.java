package com.maven.org.common.api.db;

public enum SchemaType {
    CSV("csv"), JSON("json");

    private String type;

    SchemaType(String type) {
        this.type = type;
    }

    public static SchemaType getSchemaType(String type) {
        for (SchemaType schemaType : SchemaType.values()) {
            if (schemaType.type.equals(type)) {
                return schemaType;
            }
        }
        throw new IllegalStateException("Invalid schema type - " + type);
    }

}
