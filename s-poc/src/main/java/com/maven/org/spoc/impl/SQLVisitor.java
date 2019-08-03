package com.maven.org.spoc.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import com.maven.org.common.v2.filter.And;
import com.maven.org.common.v2.filter.Contains;
import com.maven.org.common.v2.filter.EndsWith;
import com.maven.org.common.v2.filter.Eq;
import com.maven.org.common.v2.filter.Gt;
import com.maven.org.common.v2.filter.GtEq;
import com.maven.org.common.v2.filter.In;
import com.maven.org.common.v2.filter.LogicalOperator;
import com.maven.org.common.v2.filter.Lt;
import com.maven.org.common.v2.filter.LtEq;
import com.maven.org.common.v2.filter.NotEq;
import com.maven.org.common.v2.filter.NotIn;
import com.maven.org.common.v2.filter.Operator;
import com.maven.org.common.v2.filter.Or;
import com.maven.org.common.v2.filter.RelationalOperator;
import com.maven.org.common.v2.filter.StartsWith;
import com.maven.org.common.v2.filter.Visitor;
import com.maven.org.common.v2.search.Concat;
import com.maven.org.common.v2.search.Field;
import com.maven.org.common.v2.search.Lower;
import com.maven.org.common.v2.search.NumericFunction;
import com.maven.org.common.v2.search.Projection;
import com.maven.org.common.v2.search.Projection.Context;
import com.maven.org.common.v2.search.StringFunction;
import com.maven.org.common.v2.search.Sum;
import com.maven.org.common.v2.search.Upper;

public class SQLVisitor implements Visitor {

	private final String AND = "and";
	private final String OR = "or";

	private Map<String, String> attMap;

	public SQLVisitor() {
	}

	public SQLVisitor(Map<String, String> attMap) {
		this.attMap = attMap;
	}

	@Override
	public SqlExpression visit(Operator operator) {

		if (operator instanceof RelationalOperator) {
			return this.processRelationalOperator((RelationalOperator) operator);
		} else if (operator instanceof LogicalOperator) {
			return this.processLogicalOperator((LogicalOperator) operator);
		} else {
			throw new IllegalStateException("Operator not supported - " + operator.getClass());
		}
	}

	private SqlExpression processLogicalOperator(LogicalOperator logicalOperator) {
		Iterator<SqlExpression> cypherExpressions = logicalOperator.getOperators().stream().map(op -> this.visit(op))
				.collect(Collectors.toList()).iterator();

		StringBuffer query = new StringBuffer();

		String opString = this.getLogicalOperatorString(logicalOperator);

		List<Object> params = new ArrayList<>();
		while (cypherExpressions.hasNext()) {
			SqlExpression expression = cypherExpressions.next();
			query.append("(").append(expression.getSql()).append(")");
			if (cypherExpressions.hasNext()) {
				query.append(" ").append(opString).append(" ");
			}
			params.addAll(expression.getParams());
		}

		return SqlExpression.of(query.toString(), params);
	}

	private String getLogicalOperatorString(LogicalOperator logicalOperator) {
		if (logicalOperator instanceof And) {
			return AND;
		} else if (logicalOperator instanceof Or) {
			return OR;
		} else {
			throw new IllegalStateException("Logical operator not supported - " + logicalOperator.getClass());
		}
	}

	private SqlExpression processRelationalOperator(RelationalOperator rOp) {

		Object value = rOp.getValue();
		if (rOp instanceof Eq) {
			Eq eq = (Eq) rOp;
			Pair<String, List<Object>> result = this.processCasesensitiveOperator("=", eq.isCaseSensitive(),
					this.getAttributeValue(rOp.getAttribute()), value, Optional.empty());
			return this.getExpression(result.getLeft(), result.getRight());
		} else if (rOp instanceof NotEq) {
			NotEq neq = (NotEq) rOp;
			Pair<String, List<Object>> result = this.processCasesensitiveOperator("<>", neq.isCaseSensitive(),
					this.getAttributeValue(rOp.getAttribute()), value, Optional.empty());
			return this.getExpression(result.getLeft(), result.getRight());
		} else if (rOp instanceof In) {
			In in = (In) rOp;
			Pair<String, List<Object>> result = this.processCasesensitiveOperator(" in (", in.isCaseSensitive(),
					this.getAttributeValue(rOp.getAttribute()), value, Optional.of(")"));
			return this.getExpression(result.getLeft(), result.getRight());
		} else if (rOp instanceof NotIn) {
			NotIn nIn = (NotIn) rOp;
			Pair<String, List<Object>> result = this.processCasesensitiveOperator(" not in (", nIn.isCaseSensitive(),
					this.getAttributeValue(rOp.getAttribute()), value, Optional.of(")"));
			return this.getExpression(result.getLeft(), result.getRight());
		} else if (rOp instanceof StartsWith) {
			StartsWith startsWith = (StartsWith) rOp;
			Pair<String, List<Object>> result = this.processCasesensitiveOperator(" like ",
					startsWith.isCaseSensitive(), this.getAttributeValue(rOp.getAttribute()),
					new StringBuffer().append(value).append("%").toString(), Optional.empty());
			return this.getExpression(result.getLeft(), result.getRight());
		} else if (rOp instanceof EndsWith) {
			EndsWith endsWith = (EndsWith) rOp;
			Pair<String, List<Object>> result = this.processCasesensitiveOperator(" like ", endsWith.isCaseSensitive(),
					this.getAttributeValue(rOp.getAttribute()), new StringBuffer("%").append(value).toString(),
					Optional.empty());
			return this.getExpression(result.getLeft(), result.getRight());
		} else if (rOp instanceof Contains) {
			Contains constains = (Contains) rOp;
			Pair<String, List<Object>> result = this.processCasesensitiveOperator(" like ", constains.isCaseSensitive(),
					this.getAttributeValue(rOp.getAttribute()),
					new StringBuffer("%").append(value).append("%").toString(), Optional.empty());
			return this.getExpression(result.getLeft(), result.getRight());
		} else if (rOp instanceof Gt) {
			StringBuffer query = new StringBuffer();
			query.append(this.getAttributeValue(rOp.getAttribute())).append(" > ").append("? ");
			return this.getExpression(query.toString(), rOp.getValue());
		} else if (rOp instanceof GtEq) {
			StringBuffer query = new StringBuffer();
			query.append(this.getAttributeValue(rOp.getAttribute())).append(" >= ").append("? ");
			return this.getExpression(query.toString(), rOp.getValue());
		} else if (rOp instanceof Lt) {
			StringBuffer query = new StringBuffer();
			query.append(this.getAttributeValue(rOp.getAttribute())).append(" < ").append("? ");
			return this.getExpression(query.toString(), rOp.getValue());
		} else if (rOp instanceof LtEq) {
			StringBuffer query = new StringBuffer();
			query.append(this.getAttributeValue(rOp.getAttribute())).append(" <= ").append("? ");
			return this.getExpression(query.toString(), rOp.getValue());
		} else {
			throw new IllegalStateException("Relational operator not supported - " + rOp.getClass());
		}
	}

	private SqlExpression getExpression(String query, List<Object> params) {
		return SqlExpression.of(query, params);
	}

	private SqlExpression getExpression(String query, Object params) {
		return SqlExpression.of(query, params);
	}

	private String getAttributeValue(String attributeName) {
		if (this.attMap != null) {
			String value = this.attMap.get(attributeName);
			if (value == null) {
				throw new IllegalStateException("Attribute mapping not present for attribute - " + attributeName);
			}
			return value;
		} else {
			return attributeName;
		}
	}

	private Pair<String, List<Object>> processCasesensitiveOperator(String opStr, boolean isCaseSensitive,
			String attributeName, Object value, Optional<String> suffix) {
		StringBuffer query = new StringBuffer();
		Pair<String, List<Object>> clause = this.getListValues(value, isCaseSensitive);
		if (isCaseSensitive) {
			query.append(attributeName).append(opStr).append(clause.getLeft());
			if (suffix.isPresent()) {
				query.append(suffix.get());
			}
			return Pair.of(query.toString(), clause.getRight());
		} else {
			if (value instanceof String) {
				query.append("lower(").append(attributeName).append(") ");
			} else {
				query.append(attributeName);
			}
			query.append(opStr).append(clause.getLeft());
			if (suffix.isPresent()) {
				query.append(suffix.get());
			}
			return Pair.of(query.toString(), clause.getRight());
		}
	}

	private Pair<String, List<Object>> getListValues(Object value, boolean isCaseSensitive) {
		if (value instanceof List<?>) {
			StringBuffer clause = new StringBuffer();
			Iterator<?> values = ((List<?>) value).iterator();
			List<Object> retValues = new ArrayList<>();
			while (values.hasNext()) {
				clause.append("?");
				Object val = values.next();
				retValues.add(isCaseSensitive ? val : val.toString().toLowerCase());
				if (values.hasNext()) {
					clause.append(",");
				}
			}
			return Pair.of(clause.toString(), retValues);
		} else {
			return Pair.of("?", Collections.singletonList(isCaseSensitive ? value : value.toString().toLowerCase()));
		}
	}

	@Override
	public String visit(Field field) {
		return this.visitInternal(field, true);
	}

	private String visitInternal(Field field, boolean useAlias) {
		if (field instanceof Projection) {
			Projection pField = (Projection) field;
			String name = pField.getName();
			StringBuffer clause = new StringBuffer();
			if (pField.getContext() == Context.Select) {
				clause.append("n.").append(name);
			} else if (pField.getContext() == Context.Distinct) {
				clause.append("distinct (n.").append(name).append(")");
			} else if (pField.getContext() == Context.Constant) {
				clause.append(pField.getValue());
			} else {
				//
			}

			if (useAlias && pField.getContext() != Context.Constant) {
				clause.append(" as ").append(name).toString();

			}

			return clause.toString();
		} else if (field instanceof StringFunction) {
			return this.processStringFunction((StringFunction) field);
		} else if (field instanceof NumericFunction) {
			return this.processNumericFunction((NumericFunction) field);
		} else {
			throw new IllegalStateException("Field not supported - " + field.getClass().getName());
		}
	}

	private String processNumericFunction(NumericFunction function) {
		StringBuffer fxPart = new StringBuffer();
		if (function instanceof Sum) {
			Sum sum = (Sum) function;
			fxPart.append("sum(").append(this.visit(sum.getV1())).append(")");
		}
		return fxPart.toString();
	}

	private String processStringFunction(StringFunction function) {

		StringBuffer fxPart = new StringBuffer();
		if (function instanceof Concat) {
			Concat concat = (Concat) function;
			fxPart.append("(");
			Iterator<Field> itr = Arrays.asList(concat.getFields()).iterator();
			while (itr.hasNext()) {
				fxPart.append(this.visit(itr.next()));
				if (itr.hasNext()) {
					fxPart.append(" + ");
				}
			}
			fxPart.append(")");
		} else if (function instanceof Upper) {
			Upper upper = (Upper) function;
			fxPart.append("upper(").append(this.visitInternal(upper.getField(), false)).append(")");
		} else if (function instanceof Lower) {
			Lower lower = (Lower) function;
			fxPart.append("lower(").append(this.visitInternal(lower.getField(), false)).append(")");
		}

		return fxPart.toString();
	}

}
