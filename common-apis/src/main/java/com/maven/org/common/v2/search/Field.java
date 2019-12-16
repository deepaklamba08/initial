package com.maven.org.common.v2.search;

import com.maven.org.common.v2.filter.Expression;
import com.maven.org.common.v2.filter.ProjectionVisitor;

public interface Field {

	public Expression accept(ProjectionVisitor visitor);
}
