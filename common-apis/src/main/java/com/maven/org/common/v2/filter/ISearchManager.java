package com.maven.org.common.v2.filter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.maven.org.common.v2.search.Field;

public interface ISearchManager {

	public <T> T lookupObject(Class<T> clazz, Operator operator, Function<Map<String, Object>, T> mapper);

	public <T> T lookupObject(Class<T> clazz, Set<Field> fields, Operator operator,
			Function<Map<String, Object>, T> mapper);

	public <T> List<T> lookupObjects(Class<T> clazz, Operator operator, Function<Map<String, Object>, T> mapper);

}
