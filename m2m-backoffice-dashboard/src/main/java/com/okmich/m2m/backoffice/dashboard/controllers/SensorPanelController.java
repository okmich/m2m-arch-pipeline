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
public class SensorPanelController extends AbstractController<Sensor> {

    public SensorPanelController(UIView<Sensor> view) {
        super(view);
    }

    
    @Override
    protected Sensor prePerform(Sensor sensor) {
        sensor.setStatus(Sensor.STATUS_ACTIVE);
        return sensor;
    }

    
    @Override
    public Sensor transformPayload(String t) {
        //devId;ts;prs;tmp;vol;flv;xbf
        return Sensor.valueOf(t);
    }
}
