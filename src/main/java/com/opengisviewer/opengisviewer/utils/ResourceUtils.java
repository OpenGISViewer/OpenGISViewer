package com.opengisviewer.opengisviewer.utils;

import java.io.InputStream;

public class ResourceUtils {

    /**
     * Tool to make getting resource file streams easier.
     * @param pathFromResourceDir
     * @return
     */
    public static InputStream getResourceFileStream(String pathFromResourceDir){
        if(pathFromResourceDir.contains("resources")){
            pathFromResourceDir = pathFromResourceDir.replaceAll("resources", "");
        }
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        return classloader.getResourceAsStream("../../resources/" + pathFromResourceDir);
    }
}
