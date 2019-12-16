package com.maven.org.common.v2.search;

import com.maven.org.common.v2.filter.Expression;
import com.maven.org.common.v2.filter.ProjectionVisitor;

public abstract class FunctionField implements Field {

	@Override
	public Expression accept(ProjectionVisitor visitor) {
		return visitor.visit(this);
	}
}
