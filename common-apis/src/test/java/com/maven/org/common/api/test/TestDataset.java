package com.maven.org.common.api.test;

import com.maven.org.common.api.catalog.Catalog;
import com.maven.org.common.api.impl.CatalogService;
import com.maven.org.common.api.model.Dataset;
import com.maven.org.common.api.impl.InMemoryTable;
import com.maven.org.common.api.model.Functions;
import com.maven.org.common.api.model.Row;
import com.maven.org.common.api.model.SchemaRow;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDataset {

    public static void main(String[] args) {
        Catalog catalog = CatalogService.load("src/test/resources/test_db");

        System.out.println(catalog.listDatabases());
        System.out.println(catalog.listTables("test_db"));

    }
}
