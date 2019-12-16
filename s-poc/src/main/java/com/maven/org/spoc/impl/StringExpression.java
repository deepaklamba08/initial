package com.maven.org.spoc.impl;

import com.maven.org.common.v2.filter.Expression;

public class StringExpression implements Expression {

	private final String expression;

	public StringExpression(String expression) {
		this.expression = expression;
	}

	public String getExpression() {
		return expression;
	}

}

