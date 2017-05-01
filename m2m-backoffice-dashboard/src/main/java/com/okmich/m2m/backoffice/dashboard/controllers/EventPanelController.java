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
public class EventPanelController extends AbstractController<String[]> {

    /**
     *
     * @param view
     */
    public EventPanelController(UIView<String[]> view) {
        super(view);
    }

    @Override
    protected String[] transformPayload(String payload) {
        String[] fields = payload.split(";");
        return new String[]{fields[7], fields[15], fields[16], fields[8]};
    }

}
