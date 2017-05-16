/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard.db;

import static com.okmich.m2m.backoffice.dashboard.OptionRegistry.*;
import com.okmich.m2m.backoffice.dashboard.model.Sensor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import redis.clients.jedis.Jedis;

/**
 *
 * @author m.enudi
 */
public final class CacheServiceImpl implements CacheService {

    private static final DateFormat SDF = new SimpleDateFormat("yyyyMMddd");

    private final Jedis jedis;

    public CacheServiceImpl() {
        jedis = new Jedis(value(REDIS_SERVER_ADDRESS),
                valueAsInteger(REDIS_SERVER_PORT), 30000);
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
        Map<String, String> hashMap = jedis.hgetAll(SENSOR_HASH_KEY);
        return hashMap.values().stream().map((String t) -> Sensor.valueOf(t))
                .collect(Collectors.toList());
    }

    @Override
    public Float getDailyProduction(long ts) {
        String key = SDF.format(new Date(ts));
        String prodVal = jedis.hget(M2M_PRODUCTION, key);
        if (prodVal == null || prodVal.isEmpty()) {
            return 0.0f;
        }
        return Float.valueOf(prodVal);
    }

}
