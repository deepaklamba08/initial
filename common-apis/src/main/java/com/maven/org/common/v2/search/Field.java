package com.maven.org.common.v2.search;

import com.maven.org.common.v2.filter.Visitor;

public interface Field {

	public String accept(Visitor visitor);
}
