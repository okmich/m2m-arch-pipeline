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
public class ActionPanelController extends AbstractController<String[]> {

    public ActionPanelController(UIView view) {
        super(view);
    }

    @Override
    public void perform(String[] t) {
        this.uiView.refreshData(t);
    }

    @Override
    protected String[] transformPayload(String payload) {
        //expected input - cmd;bsdevId;arg;ts
        return payload.split(";");
    }
}
