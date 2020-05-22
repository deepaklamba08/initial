package com.maven.org.common.api.reader;

import com.maven.org.common.api.model.Row;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class CsvDataReader implements DataReader {

    @Override
    public Content read(File dataFile) throws IOException {
        try (
                Reader reader = Files.newBufferedReader(dataFile.toPath());
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        ) {
            List<CSVRecord> records = csvParser.getRecords();
            if (records.isEmpty()) {
                return null;
            }
            CSVRecord headerRow = records.get(0);
            List<String> columns = new ArrayList<>();
            headerRow.iterator().forEachRemaining(columns::add);
            List<String> unColumns = Collections.unmodifiableList(columns);

            Function<Object, Row> rowFunction = o -> mapper(unColumns, (CSVRecord) o);
            Content.ContentBuilder contentBuilder = new Content.ContentBuilder();
            contentBuilder.columns(unColumns).mapper(rowFunction);
            if (records.size() > 1) {
                records.subList(1, records.size());
            }
            return contentBuilder.build();

        }
    }

    private Row mapper(List<String> columns, CSVRecord record) {
        return null;
    }

}
