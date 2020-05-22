
package com.maven.org.common.api.model;

import java.util.HashMap;
import java.util.Map;

public class DataPersistorUtil {

	private static Map<String, DatasetPersister> datasetPersisters = new HashMap<>();

	private DataPersistorUtil(){}
	
	public static void register(DatasetPersister datasetPersister) {
		datasetPersisters.put(datasetPersister.getDatabase(), datasetPersister);
	}

	public static DatasetPersister getDatasetPersister(String database) {
		return datasetPersisters.get(database);
	}

}
