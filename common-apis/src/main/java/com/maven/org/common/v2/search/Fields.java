package com.maven.org.common.v2.search;

import java.util.Arrays;
import java.util.List;

public class Fields {

	public static Field select(String name) {
		return Projection.select(name);
	}

	public static List<Field> select(String... names) {
		return Arrays.asList(selectInternal(names));
	}

	public static Field[] selectInternal(String... names) {
		Field[] fields = new Field[names.length];
		for (int i = 0; i <= names.length; i++) {
			fields[i] = Projection.select(names[i]);
		}
		return fields;
	}

	public static Field concat(Field... fields) {
		return new Concat(fields);
	}

	public static Field concat(String... fields) {
		return new Concat(selectInternal(fields));
	}

	public static Field upper(Field field) {
		return new Upper(field);
	}

	public static Field upper(String field) {
		return new Upper(Projection.select( field));
	}

	public static Field lower(String field) {
		return new Lower(Projection.select( field));
	}

	public static Field lower(Field field) {
		return new Lower(field);
	}

	public static Field sum(Field field) {
		return new Sum(field);
	}

	public static Field constant(String alias,Object value) {
		return Projection.constants(value);
	}

}
