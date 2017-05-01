/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.db.impl;

import com.okmich.sensor.server.db.CacheService;
import static com.okmich.sensor.server.OptionRegistry.*;
import com.okmich.sensor.server.model.Sensor;
import com.okmich.sensor.server.model.SensorReading;
import java.util.List;
import java.util.stream.Collectors;
import redis.clients.jedis.Jedis;

/**
 *
 * @author m.enudi
 */
public final class CacheServiceImpl implements CacheService {

    private final Jedis jedis;

    private static final String SENSOR_HASH_KEY = "sensor";

    private static final String READING_HASH_KEY = "sensor.reading";

    public CacheServiceImpl() {
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
        jedis.del(M2M_SENSORS);
        for (Sensor s : sensors) {
            jedis.rpush(M2M_SENSORS, s.toString());
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
        return Sensor.valueOf(record);
    }

    /**
     *
     * @return
     */
    @Override
    public List<Sensor> getSensors() {
        Long len = jedis.llen(M2M_SENSORS);
        List<String> strs = jedis.lrange(M2M_SENSORS, 0, len);

        return strs.stream().map((String t) -> Sensor.valueOf(t))
                .collect(Collectors.toList());
    }

    /**
     *
     * @param sensorReading
     */
    @Override
    public void saveSensorReading(SensorReading sensorReading) {
        jedis.hset(READING_HASH_KEY,sensorReading.getDevId(), sensorReading.toString());
    }

    /**
     *
     * @param devId
     * @return
     */
    @Override
    public SensorReading getSensorReading(String devId) {
        String record = jedis.hget(READING_HASH_KEY,devId);
        if (record == null || record.trim().isEmpty()) {
            return null;
        }
        return new SensorReading(record);
    }
}
