package com.maven.org.common.v2.filter;

public interface Visitor {

	public Expression visit(Operator operator);

}
