/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.net.handler;

/**
 *
 * @author m.enudi
 */
public abstract class Handler {

    public Handler() {
    }

    public abstract String handle(String request);
}
