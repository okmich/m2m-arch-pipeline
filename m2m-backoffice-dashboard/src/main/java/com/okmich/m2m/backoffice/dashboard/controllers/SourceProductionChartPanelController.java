/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard.controllers;

import com.okmich.m2m.backoffice.dashboard.views.UIView;

/**
 *
 * @author ABME340
 */
public class SourceProductionChartPanelController implements UIController {

    /**
     * uiview
     */
    private final UIView uiview;

    /**
     * 
     * @param view 
     */
    public SourceProductionChartPanelController(UIView view) {
        this.uiview = view;
    }

    @Override
    public void process(Object t) {
        
    }

    @Override
    public void addChainControllers(UIController... controllers) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
