package com.maven.org.common.v2.search;

public final class Lower extends FunctionField implements StringFunction {

	private Field field;

	public Lower(Field field) {
		this.field = field;
	}
	
	public Field getField() {
		return field;
	}
}
