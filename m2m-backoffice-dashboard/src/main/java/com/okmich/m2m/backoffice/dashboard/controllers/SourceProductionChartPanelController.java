/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard.controllers;

import static com.okmich.m2m.backoffice.dashboard.OptionRegistry.*;
import com.okmich.m2m.backoffice.dashboard.db.CacheService;
import com.okmich.m2m.backoffice.dashboard.model.Sensor;
import com.okmich.m2m.backoffice.dashboard.model.SensorReading;
import com.okmich.m2m.backoffice.dashboard.views.UIView;

/**
 *
 * @author m.enudi
 */
public class SourceProductionChartPanelController extends AbstractController<SensorReading> {

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
    public void perform(SensorReading t) {
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
    protected SensorReading transformPayload(String payload) {
        //devId;ts;prs;tmp;vol;flv;xbf
        return new SensorReading(payload);
    }

}
