package com.opengisviewer.opengisviewer.data.file.raster;

import com.opengisviewer.opengisviewer.data.file.FileDataDriver;
import com.opengisviewer.opengisviewer.data.file.raster.utils.RasterDataType;
import org.geotools.api.coverage.grid.GridCoverage;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.opengis.geometry.Envelope;

import java.awt.*;
import java.awt.image.BufferedImage;


public abstract class RasterFileDataDriver extends FileDataDriver {


    /**
     *
     * @return envelope bounds of source data
     */
    public abstract Envelope getEnvelope();

    /**
     *
     * @return image content of source image. Can be gridded or imagery
     */
    public abstract Graphics2D getImage();

    /**
     *
     * @return grid data content from image
     */
    public abstract GridCoverage getGridCoverage();

    /**
     *
     * @return the enum value of either gridded data or raster data
     */
    public abstract RasterDataType getRasterDataType();


}
