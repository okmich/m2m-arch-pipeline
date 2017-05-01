/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard.controllers;

import static com.okmich.m2m.backoffice.dashboard.OptionRegistry.*;
import com.okmich.m2m.backoffice.dashboard.db.CacheService;
import com.okmich.m2m.backoffice.dashboard.model.Sensor;
import com.okmich.m2m.backoffice.dashboard.views.UIView;

/**
 *
 * @author ABME340
 */
public class SourceProductionChartPanelController extends AbstractController<Sensor> {

    private final CacheService cacheService;
    private final static String SOURCE_SENSOR = value(SOURCE_SENSOR_ID);

    /**
     *
     * @param view
     * @param cacheService
     */
    public SourceProductionChartPanelController(UIView view, CacheService cacheService) {
        super(view);
        this.cacheService = cacheService;
    }

    @Override
    public void process(String t) {
        //devId;ts;prs;tmp;vol;flv;xbf
        String[] fields = t.split(";");
        long ts = Long.parseLong(fields[1]);

        if (SOURCE_SENSOR.equals(fields[0])) {
            Sensor sensor = new Sensor();
            sensor.setDevId(fields[0]);
            sensor.setStatus(Sensor.STATUS_ACTIVE);
            //do some load
            perform(sensor);
        }
    }

    @Override
    public void perform(Sensor t) {
        if (!SOURCE_SENSOR.equals(t.getDevId())) {
            return;
        }
        if (Sensor.STATUS_ACTIVE.equals(t.getStatus())) {
            //get daily production from cache
            t.setCapacity(cacheService.getDailyProduction(t.getTimestamp()));
            this.uiView.refreshData(t);
        }
    }

    @Override
    protected Sensor transformPayload(String payload) {
        return null;
    }

}
