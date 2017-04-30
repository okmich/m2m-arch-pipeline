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
public class ConsolePanelController implements UIController<String> {

    private final UIView<String> uiview;

    public ConsolePanelController(UIView<String> view) {
        this.uiview = view;
    }

    @Override
    public void process(String t) {
        perform(t);
    }

    @Override
    public void addChainControllers(UIController... controllers) {

    }

    @Override
    public void perform(String t) {
        uiview.refreshData(t);
    }

}
