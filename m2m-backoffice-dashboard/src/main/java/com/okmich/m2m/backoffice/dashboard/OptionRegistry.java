/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author m.enudi
 */
public final class OptionRegistry {

    private final static Map<String, String> hashMap = new HashMap<>();
    
    public final static String KAFKA_BROKER_URL = "kafka.broker.url";
   
    public final static String KAFKA_RAW_EVENT_TOPIC = "kafka.m2m.raw.event.data.topic";
    public final static String KAFKA_ENRICHED_EVENT_TOPIC = "kafka.m2m.classified.msg.topic";
    public final static String KAFKA_LOSS_CONN_TOPIC = "kafka.m2m.loss.sensor.conn.topic";
    public final static String KAFKA_ACTION_LOG_TOPIC = "kafka.m2m.action.log.topic";
    
    public final static String EXECUTOR_THREADS = "no.executor.thread";
    
    public final static String SOURCE_SENSOR_ID = "source.sensor.id";
    /**
     *
     */
    private static final Logger LOG = Logger.getLogger(OptionRegistry.class.getName());

    /**
     *
     */
    private OptionRegistry() {
    }

    /**
     *
     * @param args
     */
    public static void initialize(String[] args) {
        Properties properties = new Properties();
        String sensorConfig = System.getProperty("user.dir") + File.separator + "config.properties";
        File file = new File(sensorConfig);
        if (file.exists() && file.canRead()) {
            try {
                properties.load(new FileInputStream(file));
                loadConfig(properties);
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
                throw new RuntimeException(ex);
            }
        } else {
            try {
                properties.load(ClassLoader.getSystemResourceAsStream("config.properties"));
                //save this file to user directory
                properties.store(new FileOutputStream(sensorConfig), "Configuration");
                //load it
                loadConfig(properties);
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
                throw new RuntimeException(ex);
            }
        }

    }

    /**
     *
     * @param properties
     */
    private static void loadConfig(Properties properties) {
        properties.putAll(System.getProperties());
        //user properties file to load the hashMap
        properties.keySet().forEach((key) -> {
            hashMap.put((String) key, (String) properties.get(key));
        });
    }

    /**
     *
     * @param key
     * @return
     */
    public static String value(String key) {
        return hashMap.get(key.toLowerCase());
    }

    /**
     *
     * @param key
     * @return
     */
    public static Integer valueAsInteger(String key) {
        String refinedKey = key.toLowerCase();
        if (hashMap.containsKey(refinedKey)) {
            return Integer.parseInt(hashMap.get(refinedKey));
        }
        return null;
    }

    /**
     *
     * @param key
     * @return
     */
    public static Float valueAsFloat(String key) {
        String refinedKey = key.toLowerCase();
        if (hashMap.containsKey(refinedKey)) {
            return Float.parseFloat(hashMap.get(refinedKey));
        }
        return null;
    }

    /**
     *
     * @param key
     * @return
     */
    public static boolean containsKey(String key) {
        String refinedKey = key.toLowerCase();
        return hashMap.containsKey(refinedKey);
    }
}
