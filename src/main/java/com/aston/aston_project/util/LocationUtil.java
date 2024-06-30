package com.aston.aston_project.util;

import java.util.Arrays;

public class LocationUtil {

    private static final String SEPARATOR = ",";

    public static Double[] parse(String location) throws NumberFormatException{
        return Arrays.stream(location.split(SEPARATOR)).map(Double::parseDouble).toArray(Double[]::new);
    }
}
