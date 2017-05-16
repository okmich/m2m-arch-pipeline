/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.db;

import com.okmich.sensor.server.model.Sensor;
import java.util.List;

/**
 *
 * @author m.enudi
 */
public interface SensorChainDAO {

    String getOrgFromDevID(String devId);

    String getOrgToDevID(String devId);

    void loadSensorChain(List<Sensor> sensors);

    void saveSensorChain(String devId, String fromID, String toID);

    String getFromDevID(String devId);

    String getToDevID(String devId);
}
