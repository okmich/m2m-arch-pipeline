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
public class SensorPanelController implements UIController<Sensor> {

    private final UIView<Sensor> uiview;
    private final List<UIController<Sensor>> controllers;

    public SensorPanelController(UIView<Sensor> view) {
        this.uiview = view;
        this.controllers = new ArrayList<>();
    }

    @Override
    public void process(String t) {
        //devId;ts;prs;tmp;vol;flv;xbf;devId;ts;prs;tmp;vol;flv;xbf;dist;clz;incd
        String[] fields = t.split(";");
        Sensor sensor = new Sensor();
        sensor.setDevId(fields[7]);
        sensor.setTimestamp(Long.parseLong(fields[8]));
        sensor.setCapacity(Double.parseDouble(fields[11]));
        sensor.setFlowVelocity(Double.parseDouble(fields[12]));
        sensor.setSupplyDevId(fields[0]);
        sensor.setStatus(Sensor.STATUS_ACTIVE);
        //
        perform(sensor);
        //perform for each
        for (UIController<Sensor> t1 : this.controllers) {
            t1.perform(sensor);
        }
    }

    @Override
    public void addChainControllers(UIController<Sensor>... controllerList) {
        this.controllers.addAll(Arrays.asList(controllerList));
    }

    @Override
    public void perform(Sensor t) {
        uiview.refreshData(t);
    }

}
