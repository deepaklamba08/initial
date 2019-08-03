package com.maven.org.common.v2.filter;

import com.maven.org.common.v2.search.Field;

public interface Visitor {

	public Expression visit(Operator operator);

	public String visit(Field field);

}
