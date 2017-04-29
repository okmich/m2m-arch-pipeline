/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard.controllers;

import com.okmich.m2m.backoffice.dashboard.model.Sensor;
import com.okmich.m2m.backoffice.dashboard.views.UIView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author ABME340
 */
public class DisconnectedSensorPanelController implements UIController<Sensor> {

    private final List<UIController<Sensor>> controllers;

    /**
     *
     * @param view
     */
    public DisconnectedSensorPanelController(UIView view) {
        //do nothing
        this.controllers = new ArrayList<>();
    }

    @Override
    public void process(String t) {
        //devId;ts
        String[] fields = t.split(";");
        Sensor sensor = new Sensor();
        sensor.setDevId(fields[0]);
        sensor.setTimestamp(Long.parseLong(fields[1]));
        sensor.setStatus(Sensor.STATUS_INACTIVE);
        //
        perform(sensor);
        //perform for each
        this.controllers.stream().forEach((UIController<Sensor> t1) -> {
            t1.perform(sensor);
        });
    }

    @Override
    public void addChainControllers(UIController<Sensor>... controllerList) {
        this.controllers.addAll(Arrays.asList(controllerList));
    }

    @Override
    public void perform(Sensor t) {
        //do nothing
    }

}
