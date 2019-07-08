package com.maven.org.common.v2.search;

import com.maven.org.common.v2.filter.Visitor;

public class DistinctField implements Field {

	private String name;

	public DistinctField(String name) {
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
