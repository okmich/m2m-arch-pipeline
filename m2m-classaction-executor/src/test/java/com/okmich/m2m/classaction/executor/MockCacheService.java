/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.classaction.executor;

import com.okmich.m2m.classaction.executor.db.CacheService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author datadev
 */
public class MockCacheService implements CacheService {

    /**
     * LOG
     */
    private final static Logger LOG = Logger.getLogger(MockCacheService.class.getName());

    @Override
    public String getSensorSupplyBaseDeviceId(String devId) {
        LOG.log(Level.INFO, "get the cache value of {0}", devId);
        return devId;
    }
}
