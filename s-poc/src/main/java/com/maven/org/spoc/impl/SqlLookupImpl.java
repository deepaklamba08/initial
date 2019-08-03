package com.maven.org.spoc.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maven.org.common.v2.filter.Operator;
import com.maven.org.spoc.intf.SqlLookup;

public class SqlLookupImpl implements SqlLookup {

	private String configLocation;
	private Map<String, LookupConfig> configs;
	private ObjectMapper objectMapper;

	public SqlLookupImpl(String configLocation) {
		this.configLocation = configLocation;
		this.objectMapper = new ObjectMapper();
	}

	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public List<Map<String, Object>> lookup(String context, Operator operator) {
		LookupConfig config = this.getConfig(context);
		if (config == null) {
			throw new IllegalStateException("Could not find configuration for context - " + context);
		}

		StringBuffer query = new StringBuffer(config.getBaseQuery());
		SqlExpression expression = new SQLVisitor(config.getAttributeMap()).visit(operator);
		query.append(" where ").append(expression.getSql());

		return this.jdbcTemplate.queryForList(query.toString(), expression.getParams().toArray());
	}

	private LookupConfig getConfig(String context) {
		if (this.configs == null) {
			this.configs = this.loadConfigs();
		}
		return this.configs.get(context);
	}

	private Map<String, LookupConfig> loadConfigs() {

		List<LookupConfig> configs;
		try {
			configs = this.objectMapper.readValue(new File(this.configLocation),
					new TypeReference<List<LookupConfig>>() {
					});
		} catch (IOException e) {
			throw new IllegalStateException("Error occurred while reading configurations", e);
		}
		return configs.stream().collect(Collectors.toMap(c -> c.getConfigName(), c -> c));
	}

}
