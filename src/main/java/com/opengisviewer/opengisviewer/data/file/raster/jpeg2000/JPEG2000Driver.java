package com.opengisviewer.opengisviewer.data.file.raster.jpeg2000;


import com.opengisviewer.opengisviewer.data.file.raster.RasterFileDataDriver;
import com.opengisviewer.opengisviewer.data.file.raster.utils.RasterDataType;
import org.geotools.api.coverage.grid.GridCoverage;
import org.opengis.geometry.Envelope;

import java.awt.*;
import java.awt.image.BufferedImage;

public class JPEG2000Driver extends RasterFileDataDriver {
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
    public Graphics2D getImage() {
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
