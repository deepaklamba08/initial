package com.maven.org.common.v2.numric.fx;

import com.maven.org.common.v2.filter.Visitor;
import com.maven.org.common.v2.search.Field;

public abstract class FunctionField implements Field {

	@Override
	public String accept(Visitor visitor) {
		return visitor.visit(this);
	}
}
