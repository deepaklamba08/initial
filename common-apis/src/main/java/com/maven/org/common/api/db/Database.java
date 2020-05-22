package com.maven.org.common.api.db;

import java.io.File;
import java.util.List;

public class Database {

    private String name;
    private File dir;
    private List<Table> tables;

    private Database(String name, File dir, List<Table> tables) {
        this.name = name;
        this.dir = dir;
        this.tables = tables;
    }

    public String getName() {
        return name;
    }

    public File getDir() {
        return dir;
    }

    public List<Table> getTables() {
        return tables;
    }

    public static class DatabaseBuilder {
        private String name;
        private File dir;
        private List<Table> tables;

        public DatabaseBuilder name(String name) {
            this.name = name;
            return this;
        }

        public DatabaseBuilder dir(File dir) {
            this.dir = dir;
            return this;
        }

        public DatabaseBuilder tables(List<Table> tables) {
            this.tables = tables;
            return this;
        }


        public Database build() {
            return new Database(name, dir, tables);
        }

    }
}
