/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.classaction.executor;

import com.okmich.m2m.classaction.executor.db.CommandAuditRepo;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author datadev
 */
public class MockCommandAuditRepoImpl implements CommandAuditRepo {

    /**
     * LOG
     */
    private final static Logger LOG = Logger.getLogger(MockCommandAuditRepoImpl.class.getName());

    @Override
    public void saveCommand(String[] command) {
        LOG.log(Level.INFO, "saving the command {0} to db", command);
    }

}
