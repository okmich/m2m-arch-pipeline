/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.classaction.executor;

/**
 *
 * @author datadev
 */
public interface MessageHandler {

    void handle(String payload);
}
