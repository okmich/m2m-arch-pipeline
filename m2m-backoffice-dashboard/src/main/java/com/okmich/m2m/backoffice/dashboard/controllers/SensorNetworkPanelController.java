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
public class SensorNetworkPanelController implements UIController<Sensor> {

    /**
     *
     */
    private final UIView<Sensor> uiview;

    /**
     *
     * @param view
     */
    public SensorNetworkPanelController(UIView<Sensor> view) {
        this.uiview = view;
    }

    @Override
    public void process(String t) {

    }

    @Override
    public void addChainControllers(UIController... controllers) {

    }

    @Override
    public void perform(Sensor t) {
        uiview.refreshData(t);
    }

}
