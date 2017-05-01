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
public class NetworkStatusDistPanelController extends AbstractController<Sensor> {

    /**
     *
     * @param view
     */
    public NetworkStatusDistPanelController(UIView view) {
        super(view);
    }

    @Override
    public void process(String t) {
        //do nothing
    }

    @Override
    protected Sensor transformPayload(String payload) {
        return null;
    }

}
