/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.db.cache.impl;

import com.okmich.sensor.server.db.cache.SensorCacheService;
import com.okmich.sensor.server.model.Sensor;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 *
 * @author m.enudi
 */
public final class SensorCacheServiceImpl implements SensorCacheService {

    private JedisPool jedisPool;
    /**
     *
     */
    private static final Logger LOG = Logger.getLogger(SensorCacheServiceImpl.class.getName());

    public SensorCacheServiceImpl() {
    }

    /**
     *
     * @param sensor
     */
    @Override
    public void saveSensor(Sensor sensor) {
        try (Jedis jedis = this.jedisPool.getResource();) {
            jedis.hset(SENSOR_HASH_KEY, sensor.getDevId(), sensor.toString());
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     *
     * @param sensors
     */
    @Override
    public void saveSensors(List<Sensor> sensors) {
        try (Jedis jedis = this.jedisPool.getResource();) {
            for (Sensor s : sensors) {
                saveSensor(s);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     *
     * @param devId
     * @return
     */
    @Override
    public Sensor getSensor(String devId) {
        try (Jedis jedis = this.jedisPool.getResource();) {
            String record = jedis.hget(SENSOR_HASH_KEY, devId);
            if (record == null || record.trim().isEmpty()) {
                return null;
            }
            LOG.log(Level.INFO, record);
            return Sensor.valueOf(record);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public List<Sensor> getSensors() {
        try (Jedis jedis = this.jedisPool.getResource();) {
            Map<String, String> hashMap = jedis.hgetAll(SENSOR_HASH_KEY);
            return hashMap.values().stream().map((String t) -> Sensor.valueOf(t))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void setPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
