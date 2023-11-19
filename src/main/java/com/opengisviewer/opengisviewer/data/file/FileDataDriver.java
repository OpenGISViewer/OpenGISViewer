package com.opengisviewer.opengisviewer.data.file;

import org.geotools.api.data.FileDataStore;
import org.geotools.api.data.FileDataStoreFinder;

import java.io.File;
import java.io.IOException;

public abstract class FileDataDriver {

    /**
     * Provides a filesystem agnostic reference to a local file
     * @param file java.io.File reference
     * @return returns a filesystem agnostic reference to provided local file
     */
    public FileDataStore getFileDataStore(File file) throws IOException {
        return FileDataStoreFinder.getDataStore(file);
    }

}
