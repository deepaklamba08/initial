package com.maven.org.common.v2.filter;

public abstract class RelationalOperator implements Operator {

	protected final String attribute;
	protected final Object value;

	public RelationalOperator(String attribute, Object value) {
		this.attribute = attribute;
		this.value = value;
	}
	
	public String getAttribute() {
		return attribute;
	}
	
	public Object getValue() {
		return value;
	}

	@Override
	public Expression accept(Visitor visitor) {
		return visitor.visit(this);
	}

}
