package com.maven.org.common.v2.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class LogicalOperator implements Operator {

	private List<Operator> operators;

	public LogicalOperator(Operator... operators) {
		this.operators = new ArrayList<>(operators.length);
		this.operators.addAll(Arrays.asList(operators));
	}

	public void add(Operator operator) {
		if (this.operators == null) {
			this.operators = new ArrayList<>();
		}
		this.operators.add(operator);
	}
	
	public List<Operator> getOperators() {
		return operators;
	}

	@Override
	public Expression accept(Visitor visitor) {
		return visitor.visit(this);
	}
}
