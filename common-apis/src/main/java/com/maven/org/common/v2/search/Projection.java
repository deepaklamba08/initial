package com.maven.org.common.v2.search;

import com.maven.org.common.v2.filter.Expression;
import com.maven.org.common.v2.filter.ProjectionVisitor;

public final class Projection implements Field {
	private String name;
	private Context context;
	private Object value;

	public enum Context {
		Select, Constant, Distinct
	}

	private Projection(Context context, String name, Object value) {
		this.name = name;
		this.context = context;
		this.value = value;
	}

	protected static Projection select(String name) {
		return new Projection(Context.Select, name, null);
	}

	protected static Projection distinct(String name) {
		return new Projection(Context.Distinct, name, null);
	}

	protected static Projection constants(Object value) {
		return new Projection(Context.Constant, null, value);
	}

	public String getName() {
		return name;
	}

	public Context getContext() {
		return context;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public Expression accept(ProjectionVisitor visitor) {
		return visitor.visit(this);
	}
}
