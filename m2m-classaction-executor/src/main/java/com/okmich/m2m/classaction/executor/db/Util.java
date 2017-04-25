/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.classaction.executor.db;

import org.apache.hadoop.hbase.util.Bytes;

/**
 *
 * @author m.enudi
 */
public class Util {

    public static byte[] as(String obj) {
        if (obj == null) {
            return null;
        }
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
