package com.maven.org.common.v2.string.fx;

import com.maven.org.common.v2.numric.fx.FunctionField;
import com.maven.org.common.v2.search.Field;
import com.maven.org.common.v2.search.SelectionField;

public final class Upper extends FunctionField implements StringFunction {

	private Field field;

	public Upper(Field field) {
		this.field = field;
	}
	
	public Upper(String field) {
		this.field = new SelectionField(field);
	}

	public Field getField() {
		return field;
	}
}
