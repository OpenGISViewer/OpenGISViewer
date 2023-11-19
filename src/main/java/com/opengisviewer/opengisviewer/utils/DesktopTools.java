package com.opengisviewer.opengisviewer.utils;

import java.awt.*;

public class DesktopTools {
    /**
     *
     * @return resolution of primary display
     */
    public int getScreenResolution(){
        return Toolkit.getDefaultToolkit().getScreenResolution();
    }

    /**
     *
     * @return a 2 value integer array: width, height
     */
    public int[] getDimensionsOfPrimaryDisplay(){
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        return new int[]{width, height};
    }

}
