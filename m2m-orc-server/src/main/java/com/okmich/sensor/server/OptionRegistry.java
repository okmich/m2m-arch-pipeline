/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server;

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

    public final static String MQTT_SERVER = "mqtt.server.address";
    public final static String MQTT_SERVER_ID = "mqtt.server.clientId";
    public final static String MQTT_SERVER_DATAFLOW_REQUEST_TOPIC = "mqtt.server.dtflow_rq.topic";
    public final static String MQTT_SERVER_DATAFLOW_RESPONSE_TOPIC = "mqtt.server.dtflow_rsp.topic";
    public final static String MQTT_SERVER_DATA_SUBMIT_TOPIC = "mqtt.server.data.submit.topic";
    public final static String INIT_DATA = "init.data";
    public final static String DEVICE_ID = "devId";
    public final static String TYPE = "type";
    public final static String BS_DEV_ID = "bsdev";
    public final static String DIST_SUPPLY_STN = "dsbs";
    public final static String ADDRESS = "add";
    public final static String GEO = "geo";
    public final static String LAST_CONNECT_TIME = "lct";

    public final static String KAFKA_BROKER_URL = "kafka.broker.url";
    public final static String KAFKA_LOST_CONN_TOPIC = "kafka.loss.conn.topic";
    public final static String KAFKA_ENRICHED_MESSAGE_TOPIC = "kafka.enrich.data.topic";

    public final static String DATAFLOW_REQUEST_HANDLER_THREADS = "dataflow.hander.threads";

    public final static String HBASE_ZOOKEEPER_QUORUM = "hbase.zookeeper.quorum";
    public final static String HBASE_ZOOKEEPER_CLIENT_PORT = "hbase.zookeeper.property.clientPort";

    public final static String REDIS_SERVER_ADDRESS = "redis.server.host";
    public final static String REDIS_SERVER_PORT = "redis.server.port";

    public final static String NO_DATA_THRESHHOLD = "no.sensor.dev.data.threshhold";
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
                properties.store(new FileOutputStream(sensorConfig), "System configuration");
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
        return hashMap.get(key);
    }

    /**
     *
     * @param key
     * @return
     */
    public static Integer valueAsInteger(String key) {
        if (containsKey(key)) {
            return Integer.parseInt(hashMap.get(key));
        }
        return null;
    }

    /**
     *
     * @param key
     * @return
     */
    public static Float valueAsFloat(String key) {
        if (containsKey(key)) {
            return Float.parseFloat(hashMap.get(key));
        }
        return null;
    }

    /**
     *
     * @param key
     * @return
     */
    public static boolean containsKey(String key) {
        return hashMap.containsKey(key);
    }

    /**
     *
     * @return
     */
    public static String mqttServer() {
        return hashMap.get(MQTT_SERVER);
    }
}
