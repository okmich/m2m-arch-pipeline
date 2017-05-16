/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.net.handler;

import static com.okmich.sensor.server.OptionRegistry.*;
import com.okmich.sensor.server.db.CacheService;
import com.okmich.sensor.server.db.SensorChainDAO;
import com.okmich.sensor.server.model.Sensor;
import com.okmich.sensor.server.model.SensorReading;

/**
 *
 * @author m.enudi
 */
public class DataflowRequestHandler extends Handler {

    /**
     *
     */
    private final CacheService cacheService;
    private final SensorChainDAO sensorChainDAO;

    /**
     *
     * @param cacheService
     * @param sensorChainDAO
     */
    public DataflowRequestHandler(CacheService cacheService,
            SensorChainDAO sensorChainDAO) {
        this.cacheService = cacheService;
        this.sensorChainDAO = sensorChainDAO;
    }

    @Override
    public String handle(String request) {
        //get the devId from the request - the request is basically the devId
        String devId = request;
        if (devId == null || getSensorByDevId(request) == null) {
            throw new IllegalArgumentException("no devId in request");
        }
        //get the prev node for this devId from the chain.
        String supplyingID = sensorChainDAO.getFromDevID(devId);
        //if the a prev sensor exist, return its latest reading from cache
        //if prev node does not exist, return the INIT_DATA
        if (supplyingID != null && !supplyingID.isEmpty()) {
            //return the results of the previous flow
            SensorReading sensorReading = getDeviceCurrentData(supplyingID);
            //return the sensor reading or null if nothing is found
            return sensorReading == null ? "" : sensorReading.toString();
        } else {
            return value(INIT_DATA);
        }
    }

    /**
     *
     * @param devId
     * @return
     */
    private Sensor getSensorByDevId(String devId) {
        //db call to retrieve the previous node in the chain
        Sensor node = this.cacheService.getSensor(devId);
        if (node == null) {
            throw new IllegalArgumentException("invalid node");
        }
        return node;
    }

    /**
     *
     * @param prevDev
     * @return
     */
    private SensorReading getDeviceCurrentData(String prevDev) {
        //db call to retrieve the data for the prevDev
        return this.cacheService.getSensorReading(prevDev);
    }
}
