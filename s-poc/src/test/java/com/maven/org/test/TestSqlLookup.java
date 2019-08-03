package com.maven.org.test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.maven.org.common.v2.filter.And;
import com.maven.org.common.v2.filter.Contains;
import com.maven.org.common.v2.filter.EndsWith;
import com.maven.org.common.v2.filter.Eq;
import com.maven.org.common.v2.filter.Gt;
import com.maven.org.common.v2.filter.GtEq;
import com.maven.org.common.v2.filter.In;
import com.maven.org.common.v2.filter.Lt;
import com.maven.org.common.v2.filter.LtEq;
import com.maven.org.common.v2.filter.NotEq;
import com.maven.org.common.v2.filter.NotIn;
import com.maven.org.common.v2.filter.Operator;
import com.maven.org.common.v2.filter.Or;
import com.maven.org.common.v2.filter.StartsWith;
import com.maven.org.spoc.intf.SqlLookup;

public class TestSqlLookup {

	private ClassPathXmlApplicationContext context;
	private SqlLookup sqlLookup;

	@BeforeSuite
	public void init() {
		this.context = new ClassPathXmlApplicationContext("test-context.xml");
		this.sqlLookup = context.getBean(SqlLookup.class);
	}

	@Test
	public void testAndOperator() {
		Operator operator = new And(new Eq("student name", "ricky"), new Eq("email id", "ricky@yahoo.com"));
		List<Map<String, Object>> result = sqlLookup.lookup("simple-lookup", operator);
		Assert.assertEquals(result.size(), 1);
	}

	@Test
	public void testEqOperator() {
		Operator operator = new Eq("student name", "Ricky");
		List<Map<String, Object>> result = sqlLookup.lookup("simple-lookup", operator);
		Assert.assertEquals(result.size(), 1);
	}

	@Test
	public void testEqCaseSensitiveOperator() {
		Operator operator = new Eq("student name", "ricky", true);
		List<Map<String, Object>> result = sqlLookup.lookup("simple-lookup", operator);
		Assert.assertEquals(result.size(), 1);
	}

	@Test
	public void testNotEqOperator() {
		Operator operator = new NotEq("student name", "Kevin");
		List<Map<String, Object>> result = sqlLookup.lookup("simple-lookup", operator);
		Assert.assertEquals(result.size(), 2);
	}

	@Test
	public void testNotEqCaseSensitiveOperator() {
		Operator operator = new NotEq("student name", "kevin", true);
		List<Map<String, Object>> result = sqlLookup.lookup("simple-lookup", operator);
		Assert.assertEquals(result.size(), 2);
	}

	@Test
	public void testStartsWithOperator() {
		Operator operator = new StartsWith("student name", "ri");
		List<Map<String, Object>> result = sqlLookup.lookup("simple-lookup", operator);
		Assert.assertEquals(result.size(), 1);

	}

	@Test
	public void testStartsWithCaseSensitiveOperator() {
		Operator operator = new StartsWith("student name", "ri", true);
		List<Map<String, Object>> result = sqlLookup.lookup("simple-lookup", operator);
		Assert.assertEquals(result.size(), 1);
	}

	@Test
	public void testEndsWithOperator() {
		Operator operator = new EndsWith("student name", "es");
		List<Map<String, Object>> result = sqlLookup.lookup("simple-lookup", operator);
		Assert.assertEquals(result.size(), 1);
	}

	@Test
	public void testEndsWithCaseSensitiveOperator() {
		Operator operator = new EndsWith("student name", "es", true);
		List<Map<String, Object>> result = sqlLookup.lookup("simple-lookup", operator);
		Assert.assertEquals(result.size(), 1);
	}

	@Test
	public void testConstinsOperator() {
		Operator operator = new Contains("student name", "VI");
		List<Map<String, Object>> result = sqlLookup.lookup("simple-lookup", operator);
		Assert.assertEquals(result.size(), 1);
	}

	@Test
	public void testConstinsCaseSensitiveOperator() {
		Operator operator = new Contains("student name", "vi", true);
		List<Map<String, Object>> result = sqlLookup.lookup("simple-lookup", operator);
		Assert.assertEquals(result.size(), 1);
	}

	@Test
	public void testInOperator() {
		Operator operator = new Or(new In("student name", Arrays.asList("KEVIN", "ricky")),
				new Eq("email id", "james@yahoo.com"));
		List<Map<String, Object>> result = sqlLookup.lookup("simple-lookup", operator);
		Assert.assertEquals(result.size(), 3);
	}

	@Test
	public void testInCaseSensitiveOperator() {
		Operator operator = new In("student name", Arrays.asList("kevin", "ricky"), true);
		List<Map<String, Object>> result = sqlLookup.lookup("simple-lookup", operator);
		Assert.assertEquals(result.size(), 2);
	}

	@Test
	public void testNotInOperator() {
		Operator operator = new NotIn("student name", Arrays.asList("KEVIN", "RICKY"));
		List<Map<String, Object>> result = sqlLookup.lookup("simple-lookup", operator);
		Assert.assertEquals(result.size(), 1);
	}

	@Test
	public void testNotInCaseSensitiveOperator() {
		Operator operator = new NotIn("student name", Arrays.asList("kevin", "ricky"), true);
		List<Map<String, Object>> result = sqlLookup.lookup("simple-lookup", operator);
		Assert.assertEquals(result.size(), 1);
	}

	@Test
	public void testGtOperator() {
		Operator operator = new Gt("student id", 1);
		List<Map<String, Object>> result = sqlLookup.lookup("simple-lookup", operator);
		Assert.assertEquals(result.size(), 2);
	}

	@Test
	public void testGtEqOperator() {
		Operator operator = new GtEq("student id", 1);
		List<Map<String, Object>> result = sqlLookup.lookup("simple-lookup", operator);
		Assert.assertEquals(result.size(), 3);
	}

	@Test
	public void testLtOperator() {
		Operator operator = new Lt("student id", 2);
		List<Map<String, Object>> result = sqlLookup.lookup("simple-lookup", operator);
		Assert.assertEquals(result.size(), 1);
	}

	@Test
	public void testLtEqOperator() {
		Operator operator = new LtEq("student id", 3);
		List<Map<String, Object>> result = sqlLookup.lookup("simple-lookup", operator);
		Assert.assertEquals(result.size(), 3);
	}

	@AfterSuite
	public void release() {
		if (this.context != null) {
			context.close();
		}
	}
}
