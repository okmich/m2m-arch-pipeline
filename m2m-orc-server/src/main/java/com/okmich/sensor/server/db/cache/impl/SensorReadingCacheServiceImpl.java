/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.db.cache.impl;

import static com.okmich.sensor.server.OptionRegistry.*;
import static com.okmich.sensor.server.db.cache.SensorCacheService.SENSOR_HASH_KEY;
import com.okmich.sensor.server.model.SensorReading;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import redis.clients.jedis.Jedis;
import com.okmich.sensor.server.db.cache.SensorReadingCacheService;
import java.util.logging.Level;
import redis.clients.jedis.JedisPool;

/**
 *
 * @author m.enudi
 */
public final class SensorReadingCacheServiceImpl implements SensorReadingCacheService {

    private static final DateFormat SDF = new SimpleDateFormat("yyyyMMddd");

    private JedisPool jedisPool;
    /**
     *
     */
    private static final Logger LOG = Logger.getLogger(SensorReadingCacheServiceImpl.class.getName());

    public SensorReadingCacheServiceImpl() {
    }

    /**
     *
     * @param sensorReading
     */
    @Override
    public void saveSensorReading(SensorReading sensorReading) {
        try (Jedis jedis = this.jedisPool.getResource();) {
            jedis.hset(READING_HASH_KEY, sensorReading.getDevId(), sensorReading.toString());
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
    public SensorReading getSensorReading(String devId) {
        try (Jedis jedis = this.jedisPool.getResource();) {
            String record = jedis.hget(READING_HASH_KEY, devId);
            if (record == null || record.trim().isEmpty()) {
                return null;
            }
            return new SensorReading(record);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void updateDailyProduction(double vol, long ts) {
        try (Jedis jedis = this.jedisPool.getResource();) {
            String key = SDF.format(new Date(ts));
            String prodVal = jedis.hget(M2M_PRODUCTION, key);
            double totalProd = 0d;
            if (prodVal != null) {
                totalProd = Double.parseDouble(prodVal);
            }
            totalProd += vol;
            jedis.hset(M2M_PRODUCTION, key, Double.toString(totalProd));
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public void setPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
