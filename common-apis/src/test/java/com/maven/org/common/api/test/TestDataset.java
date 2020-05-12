package com.maven.org.common.api.test;

import com.maven.org.common.api.Table;
import com.maven.org.common.api.impl.InMemoryTable;
import com.maven.org.common.api.model.Functions;
import com.maven.org.common.api.model.Row;
import com.maven.org.common.api.model.SchemaRow;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDataset {

    public static void main(String[] args) {

        Map<String,Object>data1=new HashMap<>();
        data1.put("_1","a");
        data1.put("_2","b");

        Map<String,Object>data2=new HashMap<>();
        data2.put("_1","a");
        data2.put("_2","b");

        List<Row> data = Arrays.asList(
                new SchemaRow(data1)
        );

        List<Row> data3 = Arrays.asList(
                new SchemaRow(data2)
        );

        Table dataset1 = new InMemoryTable(data);
        Table dataset2 = new InMemoryTable(data3);

        Table dataset = dataset1.join(dataset2, Functions.col("col_1"), "inner");
        dataset.foreach(System.out::println);

    }
}
