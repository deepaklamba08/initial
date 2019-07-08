package com.maven.org.common.v2.filter;

public class Eq extends RelationalOperator implements CaseSensitiveOperator {

	private boolean caseSensitive;

	public Eq(String attribute, Object value) {
		super(attribute, value);
	}

	public Eq(String attribute, String value, boolean caseSensitive) {
		super(attribute, value);
		this.caseSensitive = caseSensitive;
	}

	@Override
	public boolean isCaseSensitive() {
		return caseSensitive;
	}

}
