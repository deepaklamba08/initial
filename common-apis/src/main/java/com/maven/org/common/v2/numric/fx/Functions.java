package com.maven.org.common.v2.numric.fx;

import com.maven.org.common.v2.search.Field;

public interface Functions {

	public Field concat(Field f1, Field f2, String v);

	public Field concat(String f1, String f2, String v);

	public Field sum(Field field);

	public Field sum(String field);

	public Field upper(Field field);

	public Field upper(String field);

	public Field lower(Field field);

	public Field lower(String field);
}
