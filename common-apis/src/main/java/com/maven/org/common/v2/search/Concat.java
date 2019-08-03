package com.maven.org.common.v2.search;

public final class Concat extends FunctionField implements StringFunction {

	private Field[] fields;

	protected Concat(Field... fields) {
		this.fields = fields;
	}

	
	public Field[] getFields() {
		return fields;
	}

}
