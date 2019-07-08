package com.maven.org.common.v2.filter;

public interface Operator {

	public Expression accept(Visitor visitor);
}
