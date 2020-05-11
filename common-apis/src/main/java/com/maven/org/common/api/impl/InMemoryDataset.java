package com.maven.org.common.api.impl;

import com.maven.org.common.api.Dataset;
import com.maven.org.common.api.KeyValuedDataset;
import com.maven.org.common.v2.filter.Operator;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryDataset<T> implements Dataset<T> {

	private List<T> data;

	public InMemoryDataset(List<T> data) {
		if (data == null) {
			throw new IllegalArgumentException("data can not be null");
		}
		this.data = data;
	}
	
	@SafeVarargs
	public static <T> Dataset<T> of(T ... data){
		return new InMemoryDataset(new ArrayList<>(Arrays.asList(data)));
	}

	public Dataset<T> filter(Predicate<T> predicate) {
		return newDataset(this.data.parallelStream().filter(predicate));
	}

	public <U> Dataset<U> map(Function<T, U> mapFunction) {
		return newDataset(this.data.parallelStream().map(mapFunction));
	}

	public <U> Dataset<U> flatMap(Function<T, Iterable<U>> flatMapFunction) {
		List<U> dataList = new ArrayList<>();
		this.data.parallelStream().forEach(record -> {
			Iterable<U> records = flatMapFunction.apply(record);
			if (records != null) {
				records.forEach(r -> dataList.add(r));
			}
		});
		return new InMemoryDataset(dataList);
	}

	public T find(Predicate<T> predicate) {
		List<T> reult = this.findMany(predicate);
		return reult != null && !reult.isEmpty() ? reult.get(0) : null;
	}

	public List<T> findMany(Predicate<T> predicate) {
		return this.data.parallelStream().filter(predicate).collect(Collectors.toList());
	}

	public int size() {
		return this.data.size();
	}

	public int count(Predicate<T> predicate) {
		return this.findMany(predicate).size();
	}

	public List<T> collect() {
		return this.data;
	}

	public void foreach(Consumer<T> consumer) {
		this.data.parallelStream().forEach(consumer);
	}

	@Override
	public Dataset<T> select(String... columns) {
		return null;
	}

	@Override
	public Dataset<T> select(List<String> columns) {
		return null;
	}

	@Override
	public List<String> columns() {
		return null;
	}

	@Override
	public Dataset<T> dropColumn(String... columns) {
		return null;
	}

	@Override
	public Dataset<T> renameColumn(String oldName, String newName) {
		return null;
	}

	@Override
	public <R> Dataset<R> join(Dataset<T> other, Operator operator, String joinType) {
		return null;
	}

	private <K> InMemoryDataset newDataset(Stream<K> stream) {
		List<K> values = stream.collect(Collectors.toList());
		return new InMemoryDataset(values);
	}

	@Override
	public <K> KeyValuedDataset<K, T> groupBy(Function<T, K> keyFx) {
		Map<K, com.maven.org.common.api.Dataset<T>> map=new HashMap<>();
		this.data.parallelStream().collect(Collectors.groupingBy(keyFx)).forEach((k,v)->{
			map.put(k, new InMemoryDataset(v));
		});
		return null;
	}
}
