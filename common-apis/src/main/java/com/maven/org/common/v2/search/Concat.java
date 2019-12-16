package com.maven.org.common.v2.search;

public final class Concat extends StringFunction {

	private Field[] fields;

	protected Concat(String alias, Field... fields) {
		this.alias = alias;
		this.fields = fields;
	}

	protected Concat(Field... fields) {
		this.fields = fields;
	}

	public Field[] getFields() {
		return fields;
	}

}
