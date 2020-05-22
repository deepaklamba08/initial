package com.maven.org.common.api.db.column;

public class Column {
    private int index;
    private String name;
    private DataType dataType;

    private Column(int index, String name, DataType dataType) {
        this.index = index;
        this.name = name;
        this.dataType = dataType;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public DataType getDataType() {
        return dataType;
    }

    public static class ColumnBuilder {

        private int index;
        private String name;
        private DataType dataType;

        public ColumnBuilder index(int index) {
            this.index = index;
            return this;
        }

        public ColumnBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ColumnBuilder dataType(DataType dataType) {
            this.dataType = dataType;
            return this;
        }

        public Column build() {
            return new Column(index, name, dataType);
        }

    }
}
