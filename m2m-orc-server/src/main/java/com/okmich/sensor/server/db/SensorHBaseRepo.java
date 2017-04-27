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
public interface SensorHBaseRepo {

    /**
     *
     * @param id
     * @return
     *
     */
    Sensor findOne(String id);

    /**
     *
     *
     * @return 
     */
    List<Sensor> findAll();

    /**
     *
     * @param sensor
     * @throws Exception
     */
    void save(Sensor sensor) throws Exception;

}
