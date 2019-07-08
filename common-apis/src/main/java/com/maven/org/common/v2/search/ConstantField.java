package com.maven.org.common.v2.search;

import com.maven.org.common.v2.filter.Visitor;

public class ConstantField implements Field {

	private Object value;

	public ConstantField(Object value) {
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public String accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
