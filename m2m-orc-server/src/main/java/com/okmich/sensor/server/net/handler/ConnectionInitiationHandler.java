/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.net.handler;

import com.okmich.sensor.server.db.CacheService;
import com.okmich.sensor.server.db.SensorHBaseRepo;
import com.okmich.sensor.server.model.Sensor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author m.enudi
 */
public class ConnectionInitiationHandler extends Handler {

    /**
     * cacheService
     */
    private final CacheService cacheService;
    /**
     * sensorHBaseRepo
     */
    private final SensorHBaseRepo sensorHBaseRepo;

    private static final Logger LOG = Logger.getLogger(ConnectionInitiationHandler.class.getName());

    /**
     *
     * @param cacheService
     * @param isensorHBaseRepo
     */
    public ConnectionInitiationHandler(CacheService cacheService,
            SensorHBaseRepo isensorHBaseRepo) {
        this.cacheService = cacheService;
        this.sensorHBaseRepo = isensorHBaseRepo;
    }

    @Override
    public String handle(String request) {
        LOG.log(Level.INFO, request);
        Sensor sensor = Sensor.valueOf(request);

        Sensor sensor1 = sensorHBaseRepo.findOne(sensor.getDevId());

        try {
            if (sensor1 == null) {
                throw new IllegalArgumentException("device node does not exist");
            }
            //save the data to cache
            this.cacheService.saveSensor(sensor);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
            return null;
        }

        return "initiated";
    }
}
