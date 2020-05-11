package com.maven.org.common.api;

import com.maven.org.common.v2.filter.Operator;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Dataset<T> {

    public Dataset<T> filter(Predicate<T> predicate);

    public <U> Dataset<U> map(Function<T, U> mapFunction);

    public <U> Dataset<U> flatMap(Function<T, Iterable<U>> flatMapFunction);

    public T find(Predicate<T> predicate);

    public List<T> findMany(Predicate<T> predicate);

    public <K> KeyValuedDataset<K, T> groupBy(Function<T, K> keyFx);

    public int size();

    public int count(Predicate<T> predicate);

    public List<T> collect();

    public void foreach(Consumer<T> consumer);

    public Dataset<T> select(String... columns);

    public Dataset<T> select(List<String> columns);

    public List<String> columns();

    public Dataset<T> dropColumn(String... columns);

    public Dataset<T> renameColumn(String oldName, String newName);

    public <R> Dataset<R> join(Dataset<T> other, Operator operator, String joinType);
}
