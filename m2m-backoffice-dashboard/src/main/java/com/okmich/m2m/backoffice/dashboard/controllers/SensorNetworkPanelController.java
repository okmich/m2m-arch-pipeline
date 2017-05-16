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
 * @author ABME340
 */
public class SensorNetworkPanelController extends AbstractController<Sensor> {

    /**
     *
     * @param view
     */
    public SensorNetworkPanelController(UIView<Sensor> view) {
        super(view);
    }

    @Override
    protected Sensor transformPayload(String payload) {
        //devId;ts;prs;tmp;vol;flv;xbf;devId;ts;prs;tmp;vol;flv;xbf;fsts;dist;clz;incd
        String[] fields = payload.split(";");
        Sensor sensor = new Sensor();
        sensor.setDevId(fields[7]);
        sensor.setTimestamp(Long.parseLong(fields[8]));
        sensor.setCapacity(Double.parseDouble(fields[11]));
        sensor.setFlowVelocity(Double.parseDouble(fields[12]));
        sensor.setSupplyDevId(fields[0]);
        sensor.setStatus(fields[15]);

        return sensor;
    }

}
