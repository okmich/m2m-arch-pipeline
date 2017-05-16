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
        //devId;ts;prs;tmp;vol;flv;xbf|devId;ts;prs;tmp;vol;flv;xbf|dist
        String[] devFields = payload.split("\\|");
        String[] fields = devFields[1].split(";");
        Sensor sensor = new Sensor();
        sensor.setDevId(fields[0]);
        sensor.setTimestamp(Long.parseLong(fields[1]));
        sensor.setCapacity(Float.parseFloat(fields[4]));
        sensor.setFlowVelocity(Float.parseFloat(fields[5]));
        sensor.setSupplyDevId(devFields[0].split(";")[0]);
        sensor.setStatus(Sensor.STATUS_ACTIVE);

        return sensor;
    }

}
