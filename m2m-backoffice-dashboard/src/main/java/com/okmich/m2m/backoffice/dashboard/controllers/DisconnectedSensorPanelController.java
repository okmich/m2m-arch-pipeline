/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard.controllers;

import com.okmich.m2m.backoffice.dashboard.model.Sensor;
import com.okmich.m2m.backoffice.dashboard.views.UIView;

/**
 *
 * @author m.enudi
 */
public class DisconnectedSensorPanelController extends AbstractController<Sensor> {

    /**
     *
     * @param view
     */
    public DisconnectedSensorPanelController(UIView view) {
        super(view);
    }

    @Override
    protected Sensor transformPayload(String payload) {
        //devId;ts
        String[] fields = payload.split(";");
        Sensor sensor = new Sensor();
        sensor.setDevId(fields[0]);
        sensor.setTimestamp(Long.parseLong(fields[1]));
        sensor.setStatus(Sensor.STATUS_INACTIVE);

        return sensor;
    }
    
    @Override
    public void perform(Sensor t) {
        //do nothing
    }
}
