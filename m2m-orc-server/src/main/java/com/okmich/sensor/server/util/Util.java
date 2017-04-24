/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.hadoop.hbase.util.Bytes;

/**
 *
 * @author m.enudi
 */
public class Util {

    /**
     *
     * @param request
     * @return
     */
    public static List<String[]> parseStringData(String request) {
        String[] pairs = request.split(";");
        return Arrays.stream(pairs).map((String t) -> {
            return t.split(":");
        }).collect(Collectors.toList());
    }

    /**
     *
     * @param request
     * @return
     */
    public static Map<String, String> parseStringDataAsMap(String request) {
        String[] pairs = request.split(";");
        Map<String, String> map = new HashMap<>(pairs.length);
        String[] pair;
        for (String each : pairs) {
            pair = each.split(":");
            map.put(pair[0], pair[1].trim());
        }
        return map;
    }

    public static byte[] as(String obj) {
        return Bytes.toBytes(obj);
    }

    public static byte[] as(long obj) {
        return Bytes.toBytes(obj);
    }

    public static byte[] as(double obj) {
        return Bytes.toBytes(obj);
    }

    public static int getInt(byte[] obj) {
        return Bytes.toInt(obj);
    }

    public static Double getDouble(byte[] obj) {
        return Bytes.toDouble(obj);
    }

    public static float getFloat(byte[] obj) {
        return Bytes.toFloat(obj);
    }

    public static Long getLong(byte[] obj) {
        return Bytes.toLong(obj);
    }

    public static String getString(byte[] obj) {
        return Bytes.toString(obj);
    }
}
