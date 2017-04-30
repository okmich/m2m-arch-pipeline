/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard;

import com.okmich.m2m.backoffice.dashboard.controllers.ActionPanelController;
import com.okmich.m2m.backoffice.dashboard.controllers.SensorPanelController;
import com.okmich.m2m.backoffice.dashboard.controllers.ConsolePanelController;
import com.okmich.m2m.backoffice.dashboard.controllers.DashboardPanelController;
import com.okmich.m2m.backoffice.dashboard.controllers.DisconnectedSensorPanelController;
import com.okmich.m2m.backoffice.dashboard.controllers.EventPanelController;
import com.okmich.m2m.backoffice.dashboard.controllers.NetworkStatusDistPanelController;
import com.okmich.m2m.backoffice.dashboard.controllers.SensorNetworkPanelController;
import com.okmich.m2m.backoffice.dashboard.controllers.SourceProductionChartPanelController;
import com.okmich.m2m.backoffice.dashboard.controllers.UIController;
import com.okmich.m2m.backoffice.dashboard.messaging.KafkaMessageConsumer;
import com.okmich.m2m.backoffice.dashboard.model.Sensor;
import com.okmich.m2m.backoffice.dashboard.views.ActionPanel;
import com.okmich.m2m.backoffice.dashboard.views.SensorPanel;
import com.okmich.m2m.backoffice.dashboard.views.ConsolePanel;
import com.okmich.m2m.backoffice.dashboard.views.DashboardPanel;
import com.okmich.m2m.backoffice.dashboard.views.EventPanel;
import com.okmich.m2m.backoffice.dashboard.views.NetworkStatusDistPanel;
import com.okmich.m2m.backoffice.dashboard.views.SensorNetworkPanel;
import com.okmich.m2m.backoffice.dashboard.views.SourceProductionChartPanel;
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
        //Sensor Registry
        SensorRegistry sensorRegistry = new SensorRegistry();
        //create all views
        ActionPanel actionPanel = new ActionPanel();
        SensorPanel sensorPanel = new SensorPanel(sensorRegistry);
        ConsolePanel consolePanel = new ConsolePanel();
        DashboardPanel dashboardPanel = new DashboardPanel();
        EventPanel eventPanel = new EventPanel();
        NetworkStatusDistPanel networkStatusDistPanel = new NetworkStatusDistPanel(sensorRegistry);
        SensorNetworkPanel sensorNetworkPanel = new SensorNetworkPanel(sensorRegistry);
        SourceProductionChartPanel sourceProductionChartPanel = new SourceProductionChartPanel();

        //create all the controllers
        UIController<String> consolePanelController = new ConsolePanelController(consolePanel);
        DashboardPanelController dashboardPanelController = new DashboardPanelController(dashboardPanel);
        UIController<Sensor> networkStatusDistPanelController = new NetworkStatusDistPanelController(networkStatusDistPanel);
        UIController<Sensor> sensorNetworkPanelController
                = new SensorNetworkPanelController(sensorNetworkPanel);
        UIController<Sensor> sourceProductionChartPanelController
                = new SourceProductionChartPanelController(sourceProductionChartPanel);

        UIController<String[]> actionPanelController = new ActionPanelController(actionPanel);
        UIController<Sensor> sensorPanelController = new SensorPanelController(sensorPanel);
        UIController<Sensor> disconnectedSensorPanelController = new DisconnectedSensorPanelController(null);
        UIController<String[]> eventPanelController = new EventPanelController(eventPanel);

        //wire the contoller chains
        sensorPanelController.addChainControllers(sourceProductionChartPanelController,
                networkStatusDistPanelController, sensorNetworkPanelController);
        disconnectedSensorPanelController.addChainControllers(sensorPanelController,
                networkStatusDistPanelController,
                sensorNetworkPanelController);

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
                eventPanelController,
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
