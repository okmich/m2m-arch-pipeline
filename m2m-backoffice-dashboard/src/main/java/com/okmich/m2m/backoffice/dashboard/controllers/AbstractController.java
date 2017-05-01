/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard.controllers;

import com.okmich.m2m.backoffice.dashboard.views.UIView;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author datadev
 */
public abstract class AbstractController<T> implements UIController<T> {

    /**
     *
     */
    protected UIView<T> uiView;
    /**
     *
     */
    private final List<UIController<T>> controllerChain;

    /**
     *
     * @param uiview
     */
    public AbstractController(UIView<T> uiview) {
        this.controllerChain = new ArrayList<>();
        this.uiView = uiview;
    }

    @Override
    public void process(String payload) {
        //event loop as follows
        T t = transformPayload(payload);
        //perform
        perform(t);
        //call chain
        for (UIController<T> ctrl : controllerChain) {
            ctrl.perform(t);
        }
    }

    @Override
    public void addChainControllers(UIController<T>... controllers) {
        for (UIController<T> ctrl : controllers) {
            this.controllerChain.add(ctrl);
        }
    }

    @Override
    public void perform(T t) {
        this.uiView.refreshData(prePerform(t));
    }

    protected T prePerform(T t) {
        return t;
    }

    protected abstract T transformPayload(String payload);

}
