package com.maven.org.common.v2.search;

public final class Upper extends StringFunction {

	private Field field;

	protected Upper(String alias, Field field) {
		this.alias = alias;
		this.field = field;
	}

	protected Upper(Field field) {
		this.field = field;
	}

	public Field getField() {
		return field;
	}
}
