package com.opengisviewer.opengisviewer.utils;

import java.io.InputStream;

public class ResourceUtils {

    public static InputStream getResourceFileStream(String pathFromResourceDir){
        if(pathFromResourceDir.contains("resources")){
            pathFromResourceDir = pathFromResourceDir.replaceAll("resources", "");
        }
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        return classloader.getResourceAsStream("../../resources/" + pathFromResourceDir);
    }
}
