package com.opengisviewer.opengisviewer.data.file;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class FileDataDriverTests {

    @Test
    public void getFileDataStore_invalidFile_returnsIOException() throws IOException {
        assertThrows(IOException.class, () -> {
            FileDataDriver.getFileDataStore(new File("test.txt"));
        });

    }
}
