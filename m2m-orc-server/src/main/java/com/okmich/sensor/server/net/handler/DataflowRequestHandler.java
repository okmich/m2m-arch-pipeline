/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.net.handler;

import static com.okmich.sensor.server.OptionRegistry.*;
import com.okmich.sensor.server.db.SensorChainDAO;
import com.okmich.sensor.server.model.Sensor;
import com.okmich.sensor.server.util.Util;
import java.util.List;
import com.okmich.sensor.server.db.SensorDAO;
import com.okmich.sensor.server.db.SensorReadingDAO;
import com.okmich.sensor.server.model.SensorReading;

/**
 *
 * @author m.enudi
 */
public class DataflowRequestHandler extends Handler {

    /**
     *
     */
    private final SensorDAO sensorDAO;
    private final SensorChainDAO sensorChainDAO;
    private final SensorReadingDAO sensorReadingDAO;

    /**
     *
     * @param sensorDAO
     * @param sensorChainDAO
     * @param sensorReadingDAO
     */
    public DataflowRequestHandler(SensorDAO sensorDAO,
            SensorChainDAO sensorChainDAO,
            SensorReadingDAO sensorReadingDAO) {
        this.sensorDAO = sensorDAO;
        this.sensorChainDAO = sensorChainDAO;
        this.sensorReadingDAO = sensorReadingDAO;
    }

    @Override
    public String handle(String request) {
        //get the devId from the request
        String devId = getDeviceId(request);
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
     * @param request
     * @return
     */
    private String getDeviceId(String request) {
        List<String[]> fields = Util.parseStringData(request);
        for (String[] pairs : fields) {
            if (pairs[0].equals(DEVICE_ID)) {
                return pairs[1];
            }
        }
        return null;
    }

    /**
     *
     * @param devId
     * @return
     */
    private Sensor getSensorByDevId(String devId) {
        //db call to retrieve the previous node in the chain
        Sensor node = this.sensorDAO.getSensor(devId);
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
        return this.sensorReadingDAO.getSensorLatestReading(prevDev);
    }
}
