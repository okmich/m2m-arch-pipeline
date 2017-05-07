/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.simulator;

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

    public final static String TXNFR_FREQ = "sensor.data.txns_freq"; //in seconds: default is 30
    public final static String MODE = "mode";

    public final static String MQTT_SERVER = "mqtt.server.address";
    public final static String MQTT_SERVER_DATAFLOW_REQUEST_TOPIC = "mqtt.server.dtflow_rq.topic";
    public final static String MQTT_SERVER_DATAFLOW_RESPONSE_TOPIC = "mqtt.server.dtflow_rsp.topic";
    public final static String MQTT_SERVER_DATA_SUBMIT_TOPIC = "mqtt.server.data.submit.topic";
    public final static String MQTT_SERVER_COMMADN_RECEIPT_TOPIC = "mqtt.server.cmd.recpt.topic";

    public final static String DEVICE_ID = "sensor.id";
    public final static String TYPE = "sensor.type";
    public final static String SUPPLY_DEV_ID = "sensor.sdev";
    public final static String BS_DEV_ID = "sensor.bsdev";
    public final static String DIST_SUPPLY_DEV = "sensor.dsbs";
    public final static String ADDRESS = "sensor.add";
    public final static String GEO = "sensor.geo";

    public final static String RANGE_PRESSURE = "sensor.reading.range.prs";
    public final static String RANGE_TEMPERATURE = "sensor.reading.range.tmp";
    public final static String RANGE_FLOW_VELOCITY = "sensor.reading.range.flv";
    public final static String RANGE_VOLUME = "sensor.reading.range.vol";
    public final static String RANGE_EXTERNAL_BODY_FORCE = "sensor.reading.range.xbf";

    public final static String INIT_DATA = "init.data";

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
        String sensorConfig = System.getProperty("user.dir") + File.separator + "sensor.properties";
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
                properties.load(ClassLoader.getSystemResourceAsStream("sensor.properties"));
                //save this file to user directory
                properties.store(new FileOutputStream(sensorConfig), "Sensor configuration");
                //load it
                loadConfig(properties);
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
                throw new RuntimeException(ex);
            }
        }
//        properties.list(System.out);
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
     * @return
     */
    public static String mqttServer() {
        return value(MQTT_SERVER);
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
