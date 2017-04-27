/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard;

import com.okmich.m2m.backoffice.dashboard.messaging.KafkaMessageConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 *
 * @author datadev
 */
public final class Main {

    /**
     *
     */
    private KafkaMessageConsumer kafkaMessageConsumer;
    private MainGUIFrame mainGUIFrame;
    /**
     * LOG
     */
    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    private Main() {
        buildApplicationContext();
    }

    public static void main(String[] args) {
        //initialize registry
        OptionRegistry.initialize(args);
        Main main;
        try {
            main = new Main();
            //start
            main.start();
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
            System.exit(-1);
        }
    }

    private void buildApplicationContext() {
        //create all the controllers
        
        //create all views
        
        
        //create the main gui
        
        //create kafka consumer
    }

    public void start() {
        //start the gui
        SwingUtilities.invokeLater(() -> {
            mainGUIFrame.setVisible(true);
        });
        //start kafka listener
        kafkaMessageConsumer.start();
    }
}
