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
public class NetworkStatusDistPanelController extends AbstractController<SensorEvent> {

    /**
     *
     * @param view
     */
    public NetworkStatusDistPanelController(UIView view) {
        super(view);
    }

    @Override
    protected SensorEvent transformPayload(String payload) {
        return null;
    }

}
