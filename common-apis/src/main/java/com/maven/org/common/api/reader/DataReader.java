package com.maven.org.common.api.reader;

import java.io.File;
import java.io.IOException;

public interface DataReader {

    public Content read(File dataFile) throws IOException;

}
