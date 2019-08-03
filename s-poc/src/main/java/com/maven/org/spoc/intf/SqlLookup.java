package com.maven.org.spoc.intf;

import java.util.List;
import java.util.Map;

import com.maven.org.common.v2.filter.Operator;

public interface SqlLookup {

	public List<Map<String, Object>> lookup(String context, Operator operator);
}
