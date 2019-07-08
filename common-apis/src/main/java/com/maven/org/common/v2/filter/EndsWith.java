package com.maven.org.common.v2.filter;

public class EndsWith extends RelationalOperator implements CaseSensitiveOperator {

	private boolean caseSensitive;

	public EndsWith(String attribute, String value) {
		super(attribute, value);
	}

	public EndsWith(String attribute, String value, boolean caseSensitive) {
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
