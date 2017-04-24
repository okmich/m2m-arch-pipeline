/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.classaction.executor.mqtt;

/**
 *
 * @author datadev
 */
public interface CommandPublisher {

    /**
     *
     * @param request
     * @throws java.lang.Exception
     */
    void sendMessage(String request) throws Exception;

}
