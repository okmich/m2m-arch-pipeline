/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.db.impl;

import static com.okmich.sensor.server.OptionRegistry.*;
import com.okmich.sensor.server.model.Sensor;
import com.okmich.sensor.server.model.SensorReading;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import redis.clients.jedis.Jedis;
import com.okmich.sensor.server.db.SensorReadingCacheService;

/**
 *
 * @author m.enudi
 */
public final class SensorReadingCacheServiceImpl implements SensorReadingCacheService {

    private static final DateFormat SDF = new SimpleDateFormat("yyyyMMddd");

    private final Jedis jedis;
    /**
     *
     */
    private static final Logger LOG = Logger.getLogger(SensorReadingCacheServiceImpl.class.getName());

    public SensorReadingCacheServiceImpl() {
        jedis = new Jedis(value(REDIS_SERVER_ADDRESS),
                valueAsInteger(REDIS_SERVER_PORT), 30000);
    }

    /**
     *
     * @param sensorReading
     */
    @Override
    public void saveSensorReading(SensorReading sensorReading) {
        jedis.hset(READING_HASH_KEY, sensorReading.getDevId(), sensorReading.toString());
    }

    /**
     *
     * @param devId
     * @return
     */
    @Override
    public SensorReading getSensorReading(String devId) {
        String record = jedis.hget(READING_HASH_KEY, devId);
        if (record == null || record.trim().isEmpty()) {
            return null;
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>> " + record);
        return new SensorReading(record);
    }

    @Override
    public void updateDailyProduction(double vol, long ts) {
        String key = SDF.format(new Date(ts));
        String prodVal = jedis.hget(M2M_PRODUCTION, key);
        double totalProd = 0d;
        if (prodVal != null) {
            totalProd = Double.parseDouble(prodVal);
        }
        totalProd += vol;
        jedis.hset(M2M_PRODUCTION, key, Double.toString(totalProd));
    }
}
