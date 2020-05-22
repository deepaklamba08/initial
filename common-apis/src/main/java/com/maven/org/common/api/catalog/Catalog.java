package com.maven.org.common.api.catalog;

import com.maven.org.common.api.db.Database;
import com.maven.org.common.api.db.Table;
import com.maven.org.common.api.db.column.Column;

import java.util.List;

public interface Catalog {

    public List<String> listDatabases();

    public Database lookupDatabase(String databaseName);

    public List<String> listTables(String databaseName);

    public Table lookupTable(String databaseName, String tableName);

    public Column lookupColumn(String databaseName, String tableName, String columnName);

}
