package com.maven.org.common.v2.filter;

import com.maven.org.common.v2.search.Field;

public interface ProjectionVisitor {

	public Expression visit(Field field);
}
