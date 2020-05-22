package com.maven.org.common.api.db;

import com.maven.org.common.api.db.column.Column;

import java.io.File;
import java.util.List;

public class Table {

    private String name;
    private File path;
    private SchemaType schemaType;
    private List<Column> columns;

    private Table(String name, File path, SchemaType schemaType, List<Column> columns) {
        this.name = name;
        this.path = path;
        this.schemaType = schemaType;
        this.columns = columns;
    }

    public String getName() {
        return name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public static class TableBuilder {
        private String name;
        private File path;
        private SchemaType schemaType;
        private List<Column> columns;

        public TableBuilder name(String name) {
            this.name = name;
            return this;
        }

        public TableBuilder path(File path) {
            this.path = path;
            return this;
        }

        public TableBuilder schemaType(SchemaType schemaType) {
            this.schemaType = schemaType;
            return this;
        }

        public TableBuilder columns(List<Column> columns) {
            this.columns = columns;
            return this;
        }

        public Table build() {
            return new Table(name, path, schemaType, columns);
        }
    }
}
