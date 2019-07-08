package com.maven.org.common.v2.filter;

public class StartsWith extends RelationalOperator implements CaseSensitiveOperator {

	private boolean caseSensitive;

	public StartsWith(String attribute, String value) {
		super(attribute, value);
	}

	public StartsWith(String attribute, String value, boolean caseSensitive) {
		super(attribute, value);
		this.caseSensitive = caseSensitive;
	}
	
	@Override
	public String getValue() {
		return super.getValue().toString();
	}

	@Override
	public boolean isCaseSensitive() {
		return this.caseSensitive;
	}

}
