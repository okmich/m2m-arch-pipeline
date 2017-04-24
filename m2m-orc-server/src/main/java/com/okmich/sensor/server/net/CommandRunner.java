/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.net;

/**
 *
 * @author m.enudi
 */
public interface CommandRunner {

    void runCommand(final String command) throws CommandException;

}
