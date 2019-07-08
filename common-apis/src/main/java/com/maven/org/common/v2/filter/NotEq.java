package com.maven.org.common.v2.filter;

public class NotEq extends RelationalOperator implements CaseSensitiveOperator {

	private boolean caseSensitive;

	public NotEq(String attribute, Object value) {
		super(attribute, value);
	}

	public NotEq(String attribute, String value, boolean caseSensitive) {
		super(attribute, value);
		this.caseSensitive = caseSensitive;
	}

	@Override
	public boolean isCaseSensitive() {
		return caseSensitive;
	}

}
