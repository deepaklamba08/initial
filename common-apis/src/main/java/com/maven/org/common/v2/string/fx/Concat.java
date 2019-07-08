package com.maven.org.common.v2.string.fx;

import com.maven.org.common.v2.numric.fx.FunctionField;
import com.maven.org.common.v2.search.Field;
import com.maven.org.common.v2.search.SelectionField;

public final class Concat extends FunctionField implements StringFunction {

	private Field[] fields;

	public Concat(Field... fields) {
		this.fields = fields;
	}

	public Concat(String... fields) {
		this.fields = new Field[fields.length];
		for (int i = 0; i < fields.length; i++) {
			this.fields[i] = new SelectionField(fields[i]);
		}
	}

	public Field[] getFields() {
		return fields;
	}

}
