package com.maven.org.common.v2.search;

public final class Upper extends FunctionField implements StringFunction {

	private Field field;

	protected Upper(Field field) {
		this.field = field;
	}
	
	public Field getField() {
		return field;
	}
}
