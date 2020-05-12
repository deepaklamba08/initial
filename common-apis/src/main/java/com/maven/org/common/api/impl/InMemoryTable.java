package com.maven.org.common.api.impl;

import com.maven.org.common.api.KeyValuedTable;
import com.maven.org.common.api.model.Column;
import com.maven.org.common.api.model.Row;
import com.maven.org.common.api.Table;
import com.maven.org.common.api.model.SchemaRow;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryTable implements Table {
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
    public Table filter(Predicate<Row> predicate) {
        return newDataset(this.data.parallelStream().filter(predicate));
    }

    @Override
    public Table map(Function<Row, Row> mapFunction) {
        return newDataset(this.data.parallelStream().map(mapFunction));
    }

    @Override
    public Table flatMap(Function<Row, Iterable<Row>> flatMapFunction) {
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
    public Table select(String... columns) {
        return null;
    }

    @Override
    public Table select(Column... columns) {
        return null;
    }

    @Override
    public List<String> columns() {
        return null;
    }

    @Override
    public Table dropColumn(String... columns) {
        return null;
    }

    @Override
    public Table renameColumn(String oldName, String newName) {
        return null;
    }

    @Override
    public Table join(Table other, Column joinKey, String joinType) {
        return null;
    }

    @Override
    public Table union(Table other) {
        return null;
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
        Map<Object, List<Row>> rightDataset = this.toMap(this, joinKey);
        Map<Object, List<Row>> leftDataset = this.toMap(other, joinKey);

        if ("inner".equals(joinType)) {
            List<Row> dataItems = rightDataset.entrySet().stream().flatMap(pair -> {
                List<Row> rightRows = rightDataset.get(pair.getKey());
                List<Row> leftRows = leftDataset.get(pair.getKey());
                return leftRows == null ? Stream.empty() : merge(rightRows, leftRows, this.columns(), other.columns());
            }).collect(Collectors.toList());
            return new InMemoryDataset(dataItems);
        } else if ("left".equals(joinType)) {
            List<Row> dataItems = leftDataset.entrySet().stream().flatMap(pair -> {
                List<Row> rightRows = rightDataset.get(pair.getKey());
                List<Row> leftRows = leftDataset.get(pair.getKey());
                return merge(rightRows, leftRows, this.columns(), other.columns());
            }).collect(Collectors.toList());
            return new InMemoryDataset(dataItems);
        } else if ("right".equals(joinType)) {
            List<Row> dataItems = rightDataset.entrySet().stream().flatMap(pair -> {
                List<Row> rightRows = rightDataset.get(pair.getKey());
                List<Row> leftRows = leftDataset.get(pair.getKey());
                return merge(rightRows, leftRows, this.columns(), other.columns());
            }).collect(Collectors.toList());
            return new InMemoryDataset(dataItems);
        }
        return null;


    }

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
        Object[] finalData = new Object[rightSchema.size() + leftSchema.size()];
        String[] finalSchema = new String[rightSchema.size() + leftSchema.size()];

        Object[] rightData = rightRow == null ? ofNulls(rightSchema.size()) : ((SchemaRow) rightRow).getData();
        Object[] leftData = leftRow == null ? ofNulls(rightSchema.size()) : ((SchemaRow) leftRow).getData();

        int index = 0;
        for (; index < leftData.length; index++) {
            finalData[index] = leftRow.get(index);
            finalSchema[index] = rightSchema.get(index);
        }
        for (; index < rightSchema.size(); index++) {
            finalData[index] = rightData[index - rightSchema.size()];
            finalSchema[index] = rightSchema.get(index - rightSchema.size());
        }
        return new SchemaRow(finalData, finalSchema);
    }

    private Object[] ofNulls(int size) {
        Object[] data = new Object[size];
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        return data;
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

    private Map<Object, List<Row>> toMap(Table<T> dataset, Column joinKey) {
        Function<T, Row> mapper = this.getMapper();
        return dataset.collect().stream().map(r -> mapper.apply(r)).collect(Collectors.groupingBy(r -> r.get(joinKey.getName())));
    }*/

}
