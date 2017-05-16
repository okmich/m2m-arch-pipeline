/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard;

import com.okmich.m2m.backoffice.dashboard.controllers.*;
import com.okmich.m2m.backoffice.dashboard.db.CacheService;
import com.okmich.m2m.backoffice.dashboard.db.CacheServiceImpl;
import com.okmich.m2m.backoffice.dashboard.messaging.KafkaMessageConsumer;
import com.okmich.m2m.backoffice.dashboard.model.*;
import com.okmich.m2m.backoffice.dashboard.views.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

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
        CacheService cacheService = new CacheServiceImpl();
        //Sensor Registry
        //create all views
        ActionPanel actionPanel = new ActionPanel();
        SensorPanel sensorPanel = new SensorPanel(cacheService);
        ConsolePanel consolePanel = new ConsolePanel();
        DashboardPanel dashboardPanel = new DashboardPanel();
        EventPanel eventPanel = new EventPanel();
        NetworkStatusDistPanel networkStatusDistPanel = new NetworkStatusDistPanel(cacheService);
        SensorNetworkPanel sensorNetworkPanel = new SensorNetworkPanel(cacheService);
        SourceProductionChartPanel sourceProductionChartPanel = new SourceProductionChartPanel();

        //create all the controllers
        UIController<String> consolePanelController = new ConsolePanelController(consolePanel);
        DashboardPanelController dashboardPanelController = new DashboardPanelController(dashboardPanel);
        UIController<SensorEvent> networkStatusDistPanelController = new NetworkStatusDistPanelController(networkStatusDistPanel);
        UIController<Sensor> sensorNetworkPanelController
                = new SensorNetworkPanelController(sensorNetworkPanel);
        UIController<SensorReading> sourceProductionChartPanelController
                = new SourceProductionChartPanelController(sourceProductionChartPanel, cacheService);

        UIController<String[]> actionPanelController = new ActionPanelController(actionPanel);
        UIController<Sensor> sensorPanelController = new SensorPanelController(sensorPanel);
        UIController<Sensor> disconnectedSensorPanelController = new DisconnectedSensorPanelController(null);
        UIController<SensorEvent> eventPanelController = new EventPanelController(eventPanel);

        //wire the contoller chains
        disconnectedSensorPanelController.addChainControllers(sensorPanelController, sensorNetworkPanelController);
        eventPanelController.addChainControllers(networkStatusDistPanelController);

        //create the main gui
        mainGUIFrame = new MainGUIFrame(sensorPanel,
                eventPanel,
                actionPanel,
                sourceProductionChartPanel,
                networkStatusDistPanel,
                sensorNetworkPanel,
                consolePanel);
        //create kafka consumer
        kafkaMessageConsumer = new KafkaMessageConsumer(actionPanelController,
                sensorPanelController,
                disconnectedSensorPanelController,
                sensorNetworkPanelController,
                eventPanelController,
                sourceProductionChartPanelController,
                consolePanelController);
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
