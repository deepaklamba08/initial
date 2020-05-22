package com.maven.org.common.api.impl;

import com.maven.org.common.api.model.KeyValuedTable;
import com.maven.org.common.api.model.Row;
import com.maven.org.common.api.model.Dataset;
import com.maven.org.common.api.model.SchemaRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryTable implements Dataset {
    private List<Row> data;
    private List<String> columns;

    public InMemoryTable(List<Row> data) {
        if (data == null) {
            throw new IllegalArgumentException("data can not be null");
        }
        this.data = data;
        Row row = this.data.isEmpty() ? null : this.data.get(0);
        if (row != null && (row instanceof SchemaRow)) {
            this.columns = ((SchemaRow) row).getColumns();
        }
    }

    private InMemoryTable newDataset(Stream<Row> stream) {
        return new InMemoryTable(stream.collect(Collectors.toList()));
    }


    @Override
    public Dataset filter(Predicate<Row> predicate) {
        return newDataset(this.data.parallelStream().filter(predicate));
    }

    @Override
    public Dataset map(Function<Row, Row> mapFunction) {
        return newDataset(this.data.parallelStream().map(mapFunction));
    }

    @Override
    public Dataset flatMap(Function<Row, Iterable<Row>> flatMapFunction) {
        List<Row> dataList = new ArrayList<>();
        this.data.parallelStream().forEach(record -> {
            Iterable<Row> records = flatMapFunction.apply(record);
            if (records != null) {
                records.forEach(r -> dataList.add(r));
            }
        });
        return new InMemoryTable(dataList);
    }

    @Override
    public Row find(Predicate<Row> predicate) {
        List<Row> result = this.findMany(predicate);
        return result != null && !result.isEmpty() ? result.get(0) : null;

    }

    @Override
    public List<Row> findMany(Predicate<Row> predicate) {
        return this.data.parallelStream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public <K> KeyValuedTable<K> groupBy(Function<Row, K> keyFx) {
        this.data.parallelStream().collect(Collectors.groupingBy(keyFx)).forEach((k, v) -> {

        });
        return null;
    }

    @Override
    public int size() {
        return this.data.size();
    }

    @Override
    public int count(Predicate<Row> predicate) {
        return this.filter(predicate).size();
    }

    @Override
    public List<Row> collect() {
        return this.data;
    }

    @Override
    public void foreach(Consumer consumer) {
        this.data.forEach(consumer);
    }

    @Override
    public Dataset select(String... columns) {
        List<Row> rows = this.data.stream().map(row -> {
            Map<String, Object> data = new HashMap<>(columns.length);
            for (String col : columns) {
                data.put(col, row.get(col));
            }
            return new SchemaRow(data);
        }).collect(Collectors.toList());
        return new InMemoryTable(rows);
    }

    @Override
    public List<String> columns() {
        return this.columns;
    }

    @Override
    public Dataset dropColumn(String... columns) {
        return null;
    }

    @Override
    public Dataset renameColumn(String oldName, String newName) {
        return null;
    }
/*

    @Override
    public Dataset join(Dataset other, Column joinKey, String joinType) {
        Map<Object, List<Row>> rightTable = this.toMap(this, joinKey);
        Map<Object, List<Row>> leftTable = this.toMap(other, joinKey);
        if ("inner".equals(joinType)) {
            List<Row> dataItems = rightTable.entrySet().stream().flatMap(pair -> {
                List<Row> rightRows = rightTable.get(pair.getKey());
                List<Row> leftRows = leftTable.get(pair.getKey());
                return leftRows == null ? Stream.empty() : merge(rightRows, leftRows, this.columns(), other.columns());
            }).collect(Collectors.toList());
            return new InMemoryTable(dataItems);
        } else if ("left".equals(joinType)) {
            List<Row> dataItems = leftTable.entrySet().stream().flatMap(pair -> {
                List<Row> rightRows = rightTable.get(pair.getKey());
                List<Row> leftRows = leftTable.get(pair.getKey());
                return merge(rightRows, leftRows, this.columns(), other.columns());
            }).collect(Collectors.toList());
            return new InMemoryTable(dataItems);
        } else if ("right".equals(joinType)) {
            List<Row> dataItems = rightTable.entrySet().stream().flatMap(pair -> {
                List<Row> rightRows = rightTable.get(pair.getKey());
                List<Row> leftRows = leftTable.get(pair.getKey());
                return merge(rightRows, leftRows, this.columns(), other.columns());
            }).collect(Collectors.toList());
            return new InMemoryTable(dataItems);
        } else {
            throw new IllegalStateException("Join type not supported - " + joinType);
        }
    }
*/

    @Override
    public Dataset union(Dataset other) {
        return null;
    }

    @Override
    public void printSchema() {
        if (this.columns == null) {
            return;
        }
        StringBuilder schema = new StringBuilder();
        this.columns.forEach(column -> {
            schema.append("| ").append(column).append("\n");
        });
        System.out.println(schema);

    }

/*

    private Map<Object, List<Row>> toMap(Dataset dataset, Column joinKey) {
        return dataset.collect().stream().collect(Collectors.groupingBy(r -> r.get(joinKey.getName())));
    }

*/

    private Stream<Row> merge(List<Row> right, List<Row> left, List<String> rightSchema, List<String> leftSchema) {
        if (left == null) {
            return right.stream().map(row -> merge(row, null, rightSchema, leftSchema));
        } else if (right == null) {
            return left.stream().map(row -> merge(null, row, rightSchema, leftSchema));
        } else {
            return right.stream().flatMap(rightRow ->
                    left.stream().map(leftRow -> merge(rightRow, leftRow, rightSchema, leftSchema))
            );
        }
    }

    private Row merge(Row rightRow, Row leftRow, List<String> rightSchema, List<String> leftSchema) {
        Map<String, Object> rightData = rightRow == null ? ofNulls(rightSchema) : ((SchemaRow) rightRow).getData();
        Map<String, Object> leftData = leftRow == null ? ofNulls(leftSchema) : ((SchemaRow) leftRow).getData();
        Map<String, Object> data = new HashMap<>(leftSchema.size() + rightSchema.size());
        data.putAll(rightData);
        data.putAll(leftData);

        return new SchemaRow(data);
    }

    private Map<String, Object> ofNulls(List<String> columns) {
        Map<String, Object> data = new HashMap<>(columns.size());
        for (String column : columns) {
            data.put(column, null);
        }
        return data;
    }

    /*

    @Override
    public Table<T> select(String... columns) {
        Function<String, String> colMapper = (String a) -> a;
        return this.select(colMapper, columns);
    }

    @Override
    public Table<T> select(Column... columns) {
        Function<Column, String> colMapper = (Column a) -> a.getAlias() != null ? a.getAlias() : a.getName();
        return this.select(colMapper, columns);
    }

    @Override
    public Table<T> select(List<String> columns) {
        Function<Object, String> colMapper = (Object a) -> a.toString();
        return this.select(colMapper, columns.toArray());
    }

    @Override
    public List<String> columns() {
        return this.columns != null ? this.columns.stream().map(c -> c.getName()).collect(Collectors.toList()) : null;
    }

    @Override
    public Table<T> dropColumn(String... columns) {
        List<String> existing = this.columns();
        if (existing == null) {
            throw new IllegalStateException("Dataset is schema less");
        }
        boolean hasAll = existing.containsAll(Arrays.asList(columns));
        if (!hasAll) {
            throw new IllegalStateException("Columns specified does not exists");
        }
        existing.removeAll(Arrays.asList(columns));
        List<Row> dataItems = this.data.parallelStream().map(row -> {
            Object[] values = new Object[existing.size()];
            String[] schema = new String[existing.size()];
            int index = 0;

            for (String col : existing) {
                values[index] = row.get(col);
                schema[index] = col;
                index++;
            }
            return new SchemaRow(values, schema);
        }).collect(Collectors.toList());
        return new InMemoryDataset(dataItems);
    }

    @Override
    public Table<T> renameColumn(String oldName, String newName) {
        return null;
    }

    @Override
    public Table<T> join(Table<T> other, Column joinKey, String joinType) {



    }

    @Override
    public Table<T> union(Table<T> other) {
        return null;
    }

    private <K> Table<T> select(Function<K, String> colMapper, K... columns) {
        List<Row> dataItems = this.data.parallelStream().map(data -> {
            Row row = mapper.apply(data);
            Object[] values = new Object[columns.length];
            String[] schema = new String[columns.length];
            int index = 0;
            for (K col : columns) {
                values[index] = row.get(colMapper.apply(col));
                schema[index] = colMapper.apply(col);
                index++;
            }
            return new SchemaRow(values, schema);
        }).collect(Collectors.toList());
        return new InMemoryDataset(dataItems);
    }
*/

}
