package com.maven.org.spoc.impl;

import java.util.Arrays;
import java.util.List;

import com.maven.org.common.v2.filter.Expression;

public class SqlExpression implements Expression {

	private final String sql;
	private final List<Object> params;

	private SqlExpression(String sql, List<Object> params) {
		this.sql = sql;
		this.params = params;
	}

	public static SqlExpression of(String sql, List<Object> parameters) {
		return new SqlExpression(sql, parameters);
	}


	public static SqlExpression of(String sql, Object... parameters) {
		return new SqlExpression(sql, Arrays.asList(parameters));
	}
	
	public String getSql() {
		return sql;
	}
	
	public List<Object> getParams() {
		return params;
	}

}
