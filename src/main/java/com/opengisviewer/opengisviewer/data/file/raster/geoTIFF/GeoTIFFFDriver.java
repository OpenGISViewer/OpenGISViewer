package com.opengisviewer.opengisviewer.data.file.raster.geoTIFF;

import com.opengisviewer.opengisviewer.data.file.raster.RasterFileDataDriver;
import com.opengisviewer.opengisviewer.data.file.raster.utils.RasterDataType;
import org.geotools.api.coverage.grid.GridCoverage;
import org.geotools.api.data.DataSourceException;
import org.geotools.api.data.DataStore;
import org.geotools.api.parameter.GeneralParameterValue;
import org.geotools.api.parameter.ParameterValue;
import org.geotools.api.referencing.FactoryException;
import org.geotools.api.referencing.NoSuchAuthorityCodeException;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.coverage.grid.io.OverviewPolicy;
import org.geotools.gce.geotiff.GeoTiffFormat;
import org.geotools.gce.geotiff.GeoTiffReader;
import org.geotools.referencing.CRS;
import org.geotools.util.factory.Hints;
import org.opengis.geometry.Envelope;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GeoTIFFFDriver extends RasterFileDataDriver {
    DataStore dataStore;

    public GeoTIFFFDriver(DataStore sourceFile) throws FactoryException {
        this.dataStore = sourceFile;
    }

    public GeoTIFFFDriver(File sourceFile) throws IOException, FactoryException {
        new GeoTIFFFDriver(this.getFileDataStore(sourceFile));
    }

    /**
     * @return envelope bounds of source data
     */
    @Override
    public Envelope getEnvelope() {
        try {
            CoordinateReferenceSystem crs = CRS.decode("EPSG:4326");
            GeoTiffFormat format = new GeoTiffFormat();
            Hints hint = new Hints();
            hint.put(Hints.DEFAULT_COORDINATE_REFERENCE_SYSTEM, crs);
            hint.put(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE);

            GeoTiffReader tiffReader = format.getReader(this.dataStore, hint);
            GridCoverage2D coverage = tiffReader.read(null);
            return (Envelope) coverage.getEnvelope();
        } catch (FactoryException | IOException ex) {

        }
        return null;
    }

    /**
     * @return image content of source image. Can be gridded or imagery
     */
    @Override
    public BufferedImage getImage() { // TODO: how to handle imagery

        return null;
    }

    /**
     * @return grid data content from image
     */
    @Override
    public GridCoverage getGridCoverage() {
        try {
            ParameterValue<OverviewPolicy> policy = AbstractGridFormat.OVERVIEW_POLICY.createValue();
            policy.setValue(OverviewPolicy.IGNORE);
            ParameterValue<String> gridsize = AbstractGridFormat.SUGGESTED_TILE_SIZE.createValue();
            ParameterValue<Boolean> useJaiRead = AbstractGridFormat.USE_JAI_IMAGEREAD.createValue();
            useJaiRead.setValue(true);

            return new GeoTiffReader(this.dataStore).read(
                    new GeneralParameterValue[]{policy, gridsize, useJaiRead});

        } catch (IOException ex) {

        }
        return null;
    }

    /**
     * @return the enum value of either gridded data or raster data
     */
    @Override
    public RasterDataType getRasterDataType() { // TODO: need to figure out how to manage gridded and image data
        return null;
    }


}
