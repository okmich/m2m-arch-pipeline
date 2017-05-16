/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.db.impl;

import static com.okmich.sensor.server.OptionRegistry.*;
import com.okmich.sensor.server.db.SensorCacheService;
import com.okmich.sensor.server.model.Sensor;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import redis.clients.jedis.Jedis;

/**
 *
 * @author m.enudi
 */
public final class SensorCacheServiceImpl implements SensorCacheService {

    private final Jedis jedis;
    /**
     *
     */
    private static final Logger LOG = Logger.getLogger(SensorCacheServiceImpl.class.getName());

    public SensorCacheServiceImpl() {
        jedis = new Jedis(value(REDIS_SERVER_ADDRESS),
                valueAsInteger(REDIS_SERVER_PORT), 30000);
    }

    /**
     *
     * @param sensor
     */
    @Override
    public void saveSensor(Sensor sensor) {
        jedis.hset(SENSOR_HASH_KEY, sensor.getDevId(), sensor.toString());
    }

    /**
     *
     * @param sensors
     */
    @Override
    public void saveSensors(List<Sensor> sensors) {
        for (Sensor s : sensors) {
            saveSensor(s);
        }
    }

    /**
     *
     * @param devId
     * @return
     */
    @Override
    public Sensor getSensor(String devId) {
        String record = jedis.hget(SENSOR_HASH_KEY, devId);
        if (record == null || record.trim().isEmpty()) {
            return null;
        }
        LOG.log(Level.INFO, record);
        return Sensor.valueOf(record);
    }

    /**
     *
     * @return
     */
    @Override
    public List<Sensor> getSensors() {
        Map<String, String> hashMap = jedis.hgetAll(SENSOR_HASH_KEY);
        return hashMap.values().stream().map((String t) -> Sensor.valueOf(t))
                .collect(Collectors.toList());
    }
}
