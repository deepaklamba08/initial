package com.maven.org.common.api;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface IDataset<T> {

	public IDataset<T> filter(Predicate<T> predicate);

	public <U> IDataset<U> map(Function<T, U> mapFunction);

	public <U> IDataset<U> flatMap(Function<T, Iterable<U>> flatMapFunction);

	public T find(Predicate<T> predicate);

	public List<T> findMany(Predicate<T> predicate);

	public int size();

	public int count(Predicate<T> predicate);

	public List<T> collect();

	public void foreach(Consumer<T> consumer);

	public void persist(String database, String url, String user, char[] password);
}
