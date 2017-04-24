/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.server.net;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author m.enudi
 */
public class KafkaProducerNetworkInterface {

    private final String host;
    private final int port;

    private final ExecutorService executorService;

    /**
     *
     * @param serviceHost
     * @param servicePort
     */
    private KafkaProducerNetworkInterface(String serviceHost, int servicePort) {
        this.host = serviceHost;
        this.port = servicePort;
        this.executorService = Executors.newFixedThreadPool(2);
    }
    
    
    public void sendToStreaming(String data){
        
    }
}
