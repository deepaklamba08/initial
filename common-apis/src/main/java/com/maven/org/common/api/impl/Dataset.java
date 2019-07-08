package com.maven.org.common.api.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.maven.org.common.api.DataPersistorUtil;
import com.maven.org.common.api.DatasetPersister;
import com.maven.org.common.api.IDataset;

public class Dataset<T> implements IDataset<T> {

	private List<T> data;

	public Dataset(List<T> data) {
		if (data == null) {
			throw new IllegalArgumentException("data can not be null");
		}
		this.data = data;
	}
	
	@SafeVarargs
	public static <T> IDataset<T> of(T ... data){
		return new Dataset<T>(new ArrayList<>(Arrays.asList(data)));
	}

	public IDataset<T> filter(Predicate<T> predicate) {
		return newDataset(this.data.parallelStream().filter(predicate));
	}

	public <U> IDataset<U> map(Function<T, U> mapFunction) {
		return newDataset(this.data.parallelStream().map(mapFunction));
	}

	public <U> IDataset<U> flatMap(Function<T, Iterable<U>> flatMapFunction) {
		List<U> dataList = new ArrayList<>();
		this.data.parallelStream().forEach(record -> {
			Iterable<U> records = flatMapFunction.apply(record);
			if (records != null) {
				records.forEach(r -> dataList.add(r));
			}
		});
		return new Dataset<U>(dataList);
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

	public void persist(String database, String url, String user, char[] password) {
		DatasetPersister datapersistor = DataPersistorUtil.getDatasetPersister(database);
		if (datapersistor == null) {
			throw new IllegalStateException("no data persistor found for database - " + database);
		}
		datapersistor.persist(database, url, user, password, data);
	}

	private <K> Dataset<K> newDataset(Stream<K> stream) {
		List<K> values = stream.collect(Collectors.toList());
		return new Dataset<K>(values);
	}
}
