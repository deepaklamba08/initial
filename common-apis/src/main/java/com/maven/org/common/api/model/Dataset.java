package com.maven.org.common.api.model;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Dataset {

    public Dataset filter(Predicate<Row> predicate);

    public Dataset map(Function<Row, Row> mapFunction);

    public Dataset flatMap(Function<Row, Iterable<Row>> flatMapFunction);

    public Row find(Predicate<Row> predicate);

    public List<Row> findMany(Predicate<Row> predicate);

    public <K> KeyValuedTable<K> groupBy(Function<Row, K> keyFx);

    public int size();

    public int count(Predicate<Row> predicate);

    public List<Row> collect();

    public void foreach(Consumer consumer);

    public Dataset select(String... columns);

    public List<String> columns();

    public Dataset dropColumn(String... columns);

    public Dataset renameColumn(String oldName, String newName);

    public Dataset union(Dataset other);

    public void printSchema();
}
