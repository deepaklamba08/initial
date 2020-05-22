package com.maven.org.common.api.impl;

import com.maven.org.common.api.catalog.Catalog;
import com.maven.org.common.api.catalog.CatalogImpl;
import com.maven.org.common.api.db.Database;
import com.maven.org.common.api.db.SchemaType;
import com.maven.org.common.api.db.Table;
import com.maven.org.common.api.db.column.Column;
import com.maven.org.common.api.reader.Content;
import com.maven.org.common.api.reader.DataReader;
import com.maven.org.common.api.reader.DataReaderFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CatalogService {

    public static Catalog load(String directory) {
        File dbDirectory = new File(directory);
        if (!dbDirectory.isDirectory()) {
            throw new IllegalArgumentException("not a directory - " + directory);
        }
        Database.DatabaseBuilder databaseBuilder = createDatabase(dbDirectory.getName(), dbDirectory);

        File[] tablesDirs = dbDirectory.listFiles();
        if (tablesDirs != null && tablesDirs.length > 0) {
            List<Table> tables = Arrays.asList(dbDirectory.listFiles()).stream().filter(f -> f.isFile()).map(CatalogService::createTable).collect(Collectors.toList());
            databaseBuilder.tables(tables);
        }
        return new CatalogImpl(Collections.singletonList(databaseBuilder.build()));
    }

    private static Database.DatabaseBuilder createDatabase(String name, File directory) {
        Database.DatabaseBuilder databaseBuilder = new Database.DatabaseBuilder();
        return databaseBuilder.name(name).dir(directory);
    }

    private static Table createTable(File tableDirectory) {
        try {
            return createTableBuilder(tableDirectory).build();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private static Table.TableBuilder createTableBuilder(File tableDirectory) throws IOException {
        SchemaType schemaType = inferSchema(tableDirectory);
        Table.TableBuilder tableBuilder = new Table.TableBuilder();
        String name = tableDirectory.getName();
        tableBuilder.name(name.substring(0, name.lastIndexOf(".")))
                .path(tableDirectory)
                .schemaType(schemaType)
                .columns(createColumns(schemaType, tableDirectory));
        return tableBuilder;
    }

    private static List<Column> createColumns(SchemaType schemaType, File tableDirectory) throws IOException {
        DataReader dataReader = DataReaderFactory.createReader(schemaType);
        Content content = dataReader.read(tableDirectory);
        if (content == null || content.getColumns() == null) {
            return null;
        }

        List<Column> columns = new ArrayList<>(content.getColumns().size());
        int index = 0;
        for (String column : content.getColumns()) {
            Column.ColumnBuilder columnBuilder = new Column.ColumnBuilder();
            columnBuilder.index(index).name(column);
            columns.add(columnBuilder.build());
        }
        return columns;
    }

    private static SchemaType inferSchema(File tableDirectory) {
        String filePath = tableDirectory.getAbsolutePath();
        String fileType = filePath.substring(filePath.lastIndexOf(".") + 1);
        return SchemaType.getSchemaType(fileType);
    }
}
