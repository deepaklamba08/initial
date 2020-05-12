package com.maven.org.common.api;

import com.maven.org.common.api.model.Column;
import com.maven.org.common.api.model.Row;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Table {

    public Table filter(Predicate<Row> predicate);

    public Table map(Function<Row, Row> mapFunction);

    public Table flatMap(Function<Row, Iterable<Row>> flatMapFunction);

    public Row find(Predicate<Row> predicate);

    public List<Row> findMany(Predicate<Row> predicate);

    public <K> KeyValuedTable<K> groupBy(Function<Row, K> keyFx);

    public int size();

    public int count(Predicate<Row> predicate);

    public List<Row> collect();

    public void foreach(Consumer consumer);

    public Table select(String... columns);

    public Table select(Column... columns);

    public List<String> columns();

    public Table dropColumn(String... columns);

    public Table renameColumn(String oldName, String newName);

    public Table join(Table other, Column joinKey, String joinType);

    public Table union(Table other);
}
