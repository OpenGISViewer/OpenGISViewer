package com.opengisviewer.opengisviewer.data.file.raster.geoTIFF;

import com.opengisviewer.opengisviewer.data.file.raster.RasterFileDataDriver;
import com.opengisviewer.opengisviewer.data.file.raster.utils.RasterDataType;
import lombok.extern.slf4j.Slf4j;
import org.geotools.api.coverage.grid.GridCoverage;
import org.geotools.api.data.DataStore;
import org.geotools.api.filter.FilterFactory;
import org.geotools.api.parameter.GeneralParameterValue;
import org.geotools.api.parameter.ParameterValue;
import org.geotools.api.referencing.FactoryException;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.api.style.*;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.io.AbstractGridCoverage2DReader;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.coverage.grid.io.GridFormatFinder;
import org.geotools.coverage.grid.io.OverviewPolicy;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.gce.geotiff.GeoTiffFormat;
import org.geotools.gce.geotiff.GeoTiffReader;
import org.geotools.map.GridCoverageLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.referencing.CRS;
import org.geotools.renderer.GTRenderer;
import org.geotools.renderer.lite.StreamingRenderer;
import org.geotools.styling.SLD;
import org.geotools.util.factory.Hints;
import org.opengis.geometry.Envelope;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

@Slf4j
public class GeoTIFFFDriver extends RasterFileDataDriver {
    private DataStore dataStore;
    private RasterDataType rasterDataType;

    public GeoTIFFFDriver(DataStore sourceFile, RasterDataType rasterDataType) {
        this.dataStore = sourceFile;
        this.rasterDataType = rasterDataType;
    }

    public GeoTIFFFDriver(File sourceFile, RasterDataType rasterDataType) throws IOException, FactoryException {
        new GeoTIFFFDriver(this.getFileDataStore(sourceFile), rasterDataType);
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
            log.error(ex.toString());
        }
        return null;
    }

    /**
     * @return image content of source image. Can be gridded or imagery
     */
    @Override
    public Graphics2D getImage() {
        if (getRasterDataType() == RasterDataType.IMAGERY) { // TODO: need to test
            try {
                AbstractGridFormat format = GridFormatFinder.findFormat(this.dataStore);
                Hints hints = new Hints(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE);
                AbstractGridCoverage2DReader reader = format.getReader(this.dataStore, hints);
                GridCoverage2D grid = reader.read(null);
                reader.dispose();

                BufferedImage image = new BufferedImage(grid.getGridGeometry().getGridRange2D().width, grid.getGridGeometry().getGridRange2D().height, BufferedImage.TYPE_4BYTE_ABGR);
                MapContent mapContent = new MapContent();
                mapContent.getViewport().setCoordinateReferenceSystem(grid.getCoordinateReferenceSystem());

                WritableRaster raster = image.getRaster();

                Layer rasterLayer = new GridCoverageLayer(grid, createStyle(raster.getNumBands(), -0.4, 0.2)); // TODO: implement something like this to fix hardcoding https://gis.stackexchange.com/questions/106882/reading-each-pixel-of-each-band-of-multiband-geotiff-with-geotools-java
                mapContent.addLayer(rasterLayer);
                GTRenderer draw = new StreamingRenderer();
                draw.setMapContent(mapContent);
                Graphics2D graphics = image.createGraphics();
                draw.paint(graphics, grid.getGridGeometry().getGridRange2D(), mapContent.getMaxBounds());
                return graphics;
            } catch (IOException ex) {
                log.error("Unable to read source data file.");
            }
        }

        return null;
    }

    /**
     * @return grid data content from image
     */
    @Override
    public GridCoverage getGridCoverage() {
        if (getRasterDataType() == RasterDataType.GRIDDED) {
            try {
                ParameterValue<OverviewPolicy> policy = AbstractGridFormat.OVERVIEW_POLICY.createValue();
                policy.setValue(OverviewPolicy.IGNORE);
                ParameterValue<String> gridsize = AbstractGridFormat.SUGGESTED_TILE_SIZE.createValue();
                ParameterValue<Boolean> useJaiRead = AbstractGridFormat.USE_JAI_IMAGEREAD.createValue();
                useJaiRead.setValue(true);

                return new GeoTiffReader(this.dataStore).read(
                        new GeneralParameterValue[]{policy, gridsize, useJaiRead});

            } catch (IOException ex) {
                log.error("Unable to read source data file.");
            }
        } else if (getRasterDataType() == RasterDataType.IMAGERY) {
            log.error("Use getImage() for tiff data types with RasterDataType.Imagery");
            return null;
        }
        return null;
    }

    /**
     * @return the enum value of either gridded data or raster data
     */
    @Override
    public RasterDataType getRasterDataType() {
        return this.rasterDataType;
    }


    /**
     * @param band number of color bands
     * @param min  minimum pixel value
     * @param max  maximum pixel value
     * @return a style object taht allows AbstractGridCoverage2DReaders to interpolate pixel values and map to colors
     */
    private static Style createStyle(int band, double min, double max) {

        FilterFactory ff = CommonFactoryFinder.getFilterFactory();
        StyleFactory sf = CommonFactoryFinder.getStyleFactory();

        RasterSymbolizer sym = sf.getDefaultRasterSymbolizer();
        ColorMap cMap = sf.createColorMap();
        ColorMapEntry start = sf.createColorMapEntry();
        start.setColor(ff.literal("#ff0000")); // TODO: likely needs to make this configurable
        start.setQuantity(ff.literal(min));
        ColorMapEntry end = sf.createColorMapEntry();
        end.setColor(ff.literal("#0000ff")); // TODO: likely needs to be configurable
        end.setQuantity(ff.literal(max));

        cMap.addColorMapEntry(start);
        cMap.addColorMapEntry(end);
        sym.setColorMap(cMap);
        Style style = SLD.wrapSymbolizers(sym);

        return style;
    }


}
