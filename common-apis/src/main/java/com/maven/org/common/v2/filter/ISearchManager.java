package com.maven.org.common.v2.filter;

import java.util.Set;
import java.util.function.Function;

import com.maven.org.common.v2.search.Field;

public interface ISearchManager<K> {

	public <T> T lookupObject(String entity, Operator operator, Function<K, T> mapper);

	public <T> T lookupObject(String entity, Set<Field> fields, Operator operator, Function<K, T> mapper);

}
