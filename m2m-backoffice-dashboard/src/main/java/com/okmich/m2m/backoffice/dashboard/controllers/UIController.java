/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard.controllers;

/**
 *
 * @author ABME340
 */
public interface UIController<T> {

    void process(T payload);
    
    void addChainControllers(UIController... controllers);
    
    //void perform(T t);
}
