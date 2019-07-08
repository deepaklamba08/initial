package com.maven.org.common.v2.search;

import com.maven.org.common.v2.filter.Visitor;

public final class SelectionField implements Field {
	private String name;

	public SelectionField(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String accept(Visitor visitor) {
		return visitor.visit(this);
	}
}
