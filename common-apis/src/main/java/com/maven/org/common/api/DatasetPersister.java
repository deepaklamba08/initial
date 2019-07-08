package com.maven.org.common.api;

import java.util.List;

public interface DatasetPersister {

	public String getDatabase();

	public <T> int persist(String database, String url, String user, char[] password, List<T> data);

}
