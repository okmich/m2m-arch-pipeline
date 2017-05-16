/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard.controllers;

import com.okmich.m2m.backoffice.dashboard.model.SensorEvent;
import com.okmich.m2m.backoffice.dashboard.views.UIView;

/**
 *
 * @author m.enudi
 */
public class EventPanelController extends AbstractController<SensorEvent> {

    /**
     *
     * @param view
     */
    public EventPanelController(UIView<SensorEvent> view) {
        super(view);
    }

    @Override
    protected SensorEvent transformPayload(String payload) {
        //devId;ts;prs;tmp;vol;flv;xbf;devId;ts;prs;tmp;vol;flv;xbf;fsts;dist;clz;incd
        //00008;1494939803908;0.9922;14.8;0.00561;11.994499;0.0052000005;00009;1494939808207;1.0022;13.066667;0.0056;11.984499;0.0051000006;0.0;A;NOR;0.0
        String[] fields = payload.split(";");
        SensorEvent se = new SensorEvent();
        se.setArg(fields[17]);
        se.setClazz(fields[16]);
        se.setDevId(fields[7]);
        se.setStatus(fields[15]);
        se.setTs(Long.parseLong(fields[8]));

        return se;
    }

}
