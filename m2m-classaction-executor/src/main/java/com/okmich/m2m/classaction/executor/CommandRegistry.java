/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.classaction.executor;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author datadev
 */
public final class CommandRegistry {

    /**
     * hashMap
     */
    private static Map<String, String> hashMap = new HashMap<>();

    /**
     *
     */
    private CommandRegistry() {
    }

    /**
     *
     * @param properties
     */
    public static void initialize(Properties properties) {
        properties.keySet().forEach((key) -> {
            hashMap.put((String) key, (String) properties.get(key));
        });
    }

    /**
     *
     * @param clazz
     * @return
     */
    public static String getCommand(String clazz) {
        return hashMap.get(clazz);
    }
}
