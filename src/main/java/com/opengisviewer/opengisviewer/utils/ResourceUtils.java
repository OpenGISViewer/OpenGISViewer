package com.opengisviewer.opengisviewer.utils;

import java.io.InputStream;
import java.util.Objects;

public class ResourceUtils {

    /**
     * Tool to make getting resource file streams easier.
     * @param pathFromResourceDir
     * @return
     */
    public static InputStream getResourceFileStream(String pathFromResourceDir){
        if(pathFromResourceDir.contains("resources")){
            pathFromResourceDir = pathFromResourceDir.replaceAll("resources/", "");
        }
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        return classloader.getResourceAsStream(pathFromResourceDir);
    }


    /**
     * Returns the absolute path of the resource file.
     * @param pathFromResourceDir the path of the resource file relative to the resources directory
     * @return the absolute path of the resource file
     */
    public static String getResourceFilePath(String pathFromResourceDir){
        if(pathFromResourceDir.contains("resources")){
            pathFromResourceDir = pathFromResourceDir.replaceAll("resources/", "");
        }
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        return Objects.requireNonNull(classloader.getResource(pathFromResourceDir)).getFile();
    }
}
