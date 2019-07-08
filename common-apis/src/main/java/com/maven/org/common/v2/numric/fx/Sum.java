package com.maven.org.common.v2.numric.fx;
import com.maven.org.common.v2.search.Field;

public class Sum extends FunctionField implements NumericFunction{

	private Field v1;
	private Field v2;

	public Sum(Field v1, Field v2) {
		this.v1 = v1;
		this.v2 = v2;
	}

	public Sum(Field v1) {
		this.v1 = v1;
	}

	
	public Field getV1() {
		return v1;
	}
	
	public Field getV2() {
		return v2;
	}
	
}
