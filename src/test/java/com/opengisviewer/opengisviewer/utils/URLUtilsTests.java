package com.opengisviewer.opengisviewer.utils;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class URLUtilsTests {

    @Test
    public void testGetParamsString() {
        Map<String, String> params = new HashMap<>();
        params.put("param1", "value1");
        params.put("param2", "value2");

        String result = URLUtils.getParamsString(params);
        String expected = "param1=value1&param2=value2";
        assertEquals(expected, result);
    }

    @Test
    public void testGetParamsString_emptyMap() {
        Map<String, String> params = new HashMap<>();

        String result = URLUtils.getParamsString(params);
        String expected = "";
        assertEquals(expected, result);
    }

    @Test
    public void testGetParamsString_nullMap() {
        Map<String, String> params = null;

        assertThrows(NullPointerException.class, () -> URLUtils.getParamsString(params));
    }

    @Test
    public void testGetParamsString_baseURL() {
        Map<String, String> params = new HashMap<>();
        params.put("param1", "value1");
        params.put("param2", "value2");

        String baseURL = "https://example.com/api/";

        String result = URLUtils.getParamsString(baseURL, params);
        String expected = "https://example.com/api/param1=value1&param2=value2";
        assertEquals(expected, result);
    }

    @Test
    public void testGetParamsString_baseURL_emptyMap() {
        Map<String, String> params = new HashMap<>();

        String baseURL = "https://example.com/api/";

        String result = URLUtils.getParamsString(baseURL, params);
        String expected = "https://example.com/api/";
        assertEquals(expected, result);
    }

    @Test
    public void testGetParamsString_baseURL_nullMap() {
        Map<String, String> params = null;
        String baseURL = "https://example.com/api/";

        assertThrows(NullPointerException.class, () -> URLUtils.getParamsString(baseURL, params));
    }
}
