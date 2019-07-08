package com.maven.org.common.v2.filter;

import java.util.Arrays;
import java.util.List;

public class NotIn extends RelationalOperator implements CaseSensitiveOperator {

	private boolean caseSensitive;

	public NotIn(String attribute, List<Object> value) {
		super(attribute, value);
	}

	public NotIn(String attribute, List<String> value, boolean caseSensitive) {
		super(attribute, value);
		this.caseSensitive = caseSensitive;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getValue() {
		if (value instanceof List<?>) {
			return (List<Object>) this.value;
		} else {
			return Arrays.asList(value);
		}
	}

	@Override
	public boolean isCaseSensitive() {
		return caseSensitive;
	}

}
