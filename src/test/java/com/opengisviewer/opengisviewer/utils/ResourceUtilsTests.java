package com.opengisviewer.opengisviewer.utils;

import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ResourceUtilsTests {
    @Test
    public void getResourceFileStream_whenPathContainsResources_shouldReturnInputStream() {
        // given
        String path = "resources/logback.xml";

        // when
        InputStream inputStream = ResourceUtils.getResourceFileStream(path);

        // then
        assertNotNull(inputStream);
    }

    @Test
    public void getResourceFileStream_whenPathDoesNotContainResources_shouldReturnInputStream() {
        // given
        String path = "logback.xml";

        // when
        InputStream inputStream = ResourceUtils.getResourceFileStream(path);

        // then
        assertNotNull(inputStream);
    }

    @Test
    public void getResourceFileStream_whenPathContainsMultipleResources_shouldReturnInputStream() {
        // given
        String path = "resources/test/test.txt";

        // when
        InputStream inputStream = ResourceUtils.getResourceFileStream(path);

        // then
        assertNotNull(inputStream);
    }

    @Test
    public void getResourceFileStream_whenPathContainsInvalidResources_shouldReturnNull() {
        // given
        String path = "invalid/test.txt";

        // when
        InputStream inputStream = ResourceUtils.getResourceFileStream(path);

        // then
        assertNull(inputStream);
    }
}
