package com.maven.org.common.v2.search;

import com.maven.org.common.v2.filter.Visitor;

public abstract class FunctionField implements Field {

	@Override
	public String accept(Visitor visitor) {
		return visitor.visit(this);
	}
}
