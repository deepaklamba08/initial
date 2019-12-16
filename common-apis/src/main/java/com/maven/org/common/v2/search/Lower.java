package com.maven.org.common.v2.search;

public final class Lower extends StringFunction {

	private Field field;

	public Lower(String alias,Field field) {
		this.alias=alias;
		this.field = field;
	}
	
	public Lower(Field field) {
		this.field = field;
	}
	
	public Field getField() {
		return field;
	}
}
