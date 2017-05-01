/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard.db;

import static com.okmich.m2m.backoffice.dashboard.OptionRegistry.*;
import static com.okmich.m2m.backoffice.dashboard.db.CacheService.M2M_SENSORS;
import com.okmich.m2m.backoffice.dashboard.model.Sensor;
import java.util.List;
import java.util.stream.Collectors;
import redis.clients.jedis.Jedis;

/**
 *
 * @author m.enudi
 */
public final class CacheServiceImpl implements CacheService {

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
        String record = jedis.get(devId);
        if (record == null || record.trim().isEmpty()) {
            return null;
        }
        return new Sensor(record);
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

}
