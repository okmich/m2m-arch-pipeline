/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard.controllers;

import com.okmich.m2m.backoffice.dashboard.views.UIView;

/**
 *
 * @author m.enudi
 */
public class ConsolePanelController extends AbstractController<String> {

    public ConsolePanelController(UIView<String> view) {
        super(view);
    }

    @Override
    protected String transformPayload(String payload) {
        return payload;
    }

}
