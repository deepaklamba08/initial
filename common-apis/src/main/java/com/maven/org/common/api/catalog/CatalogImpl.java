package com.maven.org.common.api.catalog;

import com.maven.org.common.api.db.Database;
import com.maven.org.common.api.db.Table;
import com.maven.org.common.api.db.column.Column;
import javafx.scene.control.Tab;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CatalogImpl implements Catalog {

    private Map<String, Database> databaseMap;

    public CatalogImpl(List<Database> databases) {
        this.databaseMap = databases.stream().collect(Collectors.toMap(d -> d.getName(), Function.identity()));
    }

    @Override
    public List<String> listDatabases() {
        return new ArrayList<>(this.databaseMap.keySet());
    }

    @Override
    public Database lookupDatabase(String databaseName) {
        return this.databaseMap.get(databaseName);
    }

    @Override
    public List<String> listTables(String databaseName) {
        Database database = this.lookupDatabase(databaseName);
        return database != null ? database.getTables().stream().map(c -> c.getName()).collect(Collectors.toList()) : null;
    }

    @Override
    public Table lookupTable(String databaseName, String tableName) {
        Database database = this.lookupDatabase(databaseName);
        if (database == null) {
            return null;
        }
        List<Table> tables = database.getTables();
        if (tables == null) {
            return null;
        }

        BiFunction<Table, String, Boolean> biFunction = (table, s) -> table.getName().equals(s);
        return this.lookup(tables, tableName, biFunction);

    }

    @Override
    public Column lookupColumn(String databaseName, String tableName, String columnName) {
        Table table = this.lookupTable(databaseName, tableName);
        if (table == null || table.getColumns() == null) {
            return null;
        }
        BiFunction<Column, String, Boolean> biFunction = (column, s) -> column.getName().equals(s);
        return this.lookup(table.getColumns(), tableName, biFunction);
    }

    private <T> T lookup(List<T> elements, String name, BiFunction<T, String, Boolean> biFunction) {
        Optional<T> optionalT = elements.stream().filter(ele -> biFunction.apply(ele, name)).findAny();
        return optionalT.isPresent() ? optionalT.get() : null;
    }
}
