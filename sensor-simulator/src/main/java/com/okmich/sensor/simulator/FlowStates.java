/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.simulator;

/**
 *
 * @author datadev
 */
public enum FlowStates {
    NORMAL("Normal"), TURBULENCE("Turbulence"), LEAKAGE("Leakage"), DISCONNECTION("Disconnection"), STOPFLOW("StopFlow");

    private final String value;

    private FlowStates(String arg) {
        this.value = arg;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
