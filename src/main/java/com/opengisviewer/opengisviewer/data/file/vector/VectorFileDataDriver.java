package com.opengisviewer.opengisviewer.data.file.vector;

import com.opengisviewer.opengisviewer.data.file.FileDataDriver;
import org.geotools.api.data.FileDataStore;
import org.geotools.api.data.SimpleFeatureSource;

import java.io.IOException;

public class VectorFileDataDriver extends FileDataDriver {

    /**
     *
     * @param fileDataStore a filesystem agnostic reference to a local file
     * @return a feature source that allows referencing of features.
     * @throws IOException
     */
    public SimpleFeatureSource getSimpleFeatureSource(FileDataStore fileDataStore) throws IOException {
        return fileDataStore.getFeatureSource();
    }

}
