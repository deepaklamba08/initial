package com.maven.org.common.v2.search;

public class Sum extends FunctionField implements NumericFunction{

	private Field v1;

	protected Sum(Field v1) {
		this.v1 = v1;
	}

	public Field getV1() {
		return v1;
	}
	
}
