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
public class EventPanelController implements UIController<String[]> {

    /**
     *
     */
    private final UIView<String[]> uiview;

    /**
     *
     * @param view
     */
    public EventPanelController(UIView<String[]> view) {
        this.uiview = view;
    }

    @Override
    public void process(String t) {
        //devId;ts;prs;tmp;vol;flv;xbf;devId;ts;prs;tmp;vol;flv;xbf;dist;clz;incd
        String[] fields = t.split(";");
        perform(new String[]{fields[7], fields[15], fields[16], fields[8]});
    }

    @Override
    public void addChainControllers(UIController... controllers) {

    }

    @Override
    public void perform(String[] t) {
        uiview.refreshData(t);
    }

}
