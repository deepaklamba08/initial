package com.maven.org.common.v2.search;

import com.maven.org.common.v2.numric.fx.Sum;
import com.maven.org.common.v2.string.fx.Concat;
import com.maven.org.common.v2.string.fx.Lower;
import com.maven.org.common.v2.string.fx.Upper;

public class Fields {

	public static Field select(String name) {
		return new SelectionField(name);
	}

	public static Field concat(Field ... fields) {
		return new Concat(fields);
	}
	
	public static Field concat(String ... fields) {
		return new Concat(fields);
	}

	public static Field upper(Field field) {
		return new Upper(field);
	}
	
	public static Field upper(String field) {
		return new Upper(field);
	}

	public static Field lower(String field) {
		return new Lower(field);
	}

	public static Field lower(Field field) {
		return new Lower(field);
	}

	public static Field sum(Field field) {
		return new Sum(field);
	}
	
	public static Field constant(Object value) {
		return new ConstantField(value);
	}

}
