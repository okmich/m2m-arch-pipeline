/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author m.enudi
 */
public final class OptionRegistry {

    private final static Map<String, String> hashMap = new HashMap<>();

    public final static String MQTT_SERVER = "mqtt.server.address";
    public final static String MQTT_SERVER_ID = "mqtt.server.clientId";
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
    private OptionRegistry() {
    }

    /**
     *
     * @param arguments
     */
    public static void initialize(String[] arguments) {
        String pair[];
        for (String param : arguments) {
            pair = param.split("=");
            hashMap.put(pair[0].substring(1).toLowerCase(), pair[1]);
        }
        System.out.println("map" + hashMap);
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
