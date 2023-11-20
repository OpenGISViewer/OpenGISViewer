package com.opengisviewer.opengisviewer.data.file.raster.netCDF;

import com.opengisviewer.opengisviewer.data.file.raster.RasterFileDataDriver;
import com.opengisviewer.opengisviewer.data.file.raster.utils.RasterDataType;
import org.geotools.api.coverage.grid.GridCoverage;
import org.opengis.geometry.Envelope;

import java.awt.image.BufferedImage;

public class NetCDFDriver extends RasterFileDataDriver {

    /**
     * @return envelope bounds of source data
     */
    @Override
    public Envelope getEnvelope() {
        return null;
    }

    /**
     * @return image content of source image. Can be gridded or imagery
     */
    @Override
    public BufferedImage getImage() {
        return null;
    }

    /**
     * @return grid data content from image
     */
    @Override
    public GridCoverage getGridCoverage() {
        return null;
    }

    /**
     * @return the enum value of either gridded data or raster data
     */
    @Override
    public RasterDataType getRasterDataType() {
        return null;
    }
}
