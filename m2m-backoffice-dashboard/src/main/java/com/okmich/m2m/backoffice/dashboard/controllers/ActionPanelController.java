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
public class ActionPanelController implements UIController<String[]> {

    private final UIView<String[]> uiview;

    public ActionPanelController(UIView view) {
        this.uiview = view;
    }

    @Override
    public void process(String payload) {
        //expected input - cmd;bsdevId;arg;ts
        String[] fields = payload.split(";");
        perform(fields);
    }

    @Override
    public void addChainControllers(UIController... controllers) {
        
    }

    @Override
    public void perform(String[] t) {
        uiview.refreshData(t);
    }

}
