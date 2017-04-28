/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard;

import com.okmich.m2m.backoffice.dashboard.controllers.ActionPanelController;
import com.okmich.m2m.backoffice.dashboard.controllers.ConnectedSensorPanelController;
import com.okmich.m2m.backoffice.dashboard.controllers.ConsolePanelController;
import com.okmich.m2m.backoffice.dashboard.controllers.DashboardPanelController;
import com.okmich.m2m.backoffice.dashboard.controllers.DisconnectedSensorPanelController;
import com.okmich.m2m.backoffice.dashboard.controllers.EventPanelController;
import com.okmich.m2m.backoffice.dashboard.controllers.NetworkStatusDistPanelController;
import com.okmich.m2m.backoffice.dashboard.controllers.SensorNetworkPanelController;
import com.okmich.m2m.backoffice.dashboard.controllers.SourceProductionChartPanelController;
import com.okmich.m2m.backoffice.dashboard.messaging.KafkaMessageConsumer;
import com.okmich.m2m.backoffice.dashboard.views.ActionPanel;
import com.okmich.m2m.backoffice.dashboard.views.ConnectedSensorPanel;
import com.okmich.m2m.backoffice.dashboard.views.ConsolePanel;
import com.okmich.m2m.backoffice.dashboard.views.DashboardPanel;
import com.okmich.m2m.backoffice.dashboard.views.DisconnectedSensorPanel;
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
        //create all views
        ActionPanel actionPanel = new ActionPanel();
        ConnectedSensorPanel connectedSensorPanel = new ConnectedSensorPanel();
        ConsolePanel consolePanel = new ConsolePanel();
        DashboardPanel dashboardPanel = new DashboardPanel();
        DisconnectedSensorPanel disconnectedSensorPanel = new DisconnectedSensorPanel();
        EventPanel eventPanel = new EventPanel();
        NetworkStatusDistPanel networkStatusDistPanel = new NetworkStatusDistPanel();
        SensorNetworkPanel sensorNetworkPanel = new SensorNetworkPanel();
        SourceProductionChartPanel sourceProductionChartPanel = new SourceProductionChartPanel();

        //create all the controllers
        ConsolePanelController consolePanelController = new ConsolePanelController(connectedSensorPanel);
        DashboardPanelController dashboardPanelController = new DashboardPanelController(dashboardPanel);
        NetworkStatusDistPanelController networkStatusDistPanelController = new NetworkStatusDistPanelController(networkStatusDistPanel);
        SensorNetworkPanelController sensorNetworkPanelController
                = new SensorNetworkPanelController(sensorNetworkPanel);
        SourceProductionChartPanelController sourceProductionChartPanelController
                = new SourceProductionChartPanelController(sourceProductionChartPanel);

        ActionPanelController actionPanelController = new ActionPanelController(actionPanel);
        ConnectedSensorPanelController connectedSensorPanelController = new ConnectedSensorPanelController(connectedSensorPanel);
        DisconnectedSensorPanelController disconnectedSensorPanelController = new DisconnectedSensorPanelController(disconnectedSensorPanel);
        EventPanelController eventPanelController = new EventPanelController(eventPanel);

        //wire the contoller chains
        actionPanelController.addChainControllers(consolePanelController);
        connectedSensorPanelController.addChainControllers(disconnectedSensorPanelController, sourceProductionChartPanelController, 
                networkStatusDistPanelController, sensorNetworkPanelController, consolePanelController);
        disconnectedSensorPanelController.addChainControllers(connectedSensorPanelController, sensorNetworkPanelController, consolePanelController);
        eventPanelController.addChainControllers(consolePanelController);

        //create the main gui
        mainGUIFrame = new MainGUIFrame(connectedSensorPanel,
                disconnectedSensorPanel,
                eventPanel,
                actionPanel,
                sourceProductionChartPanel,
                networkStatusDistPanel,
                sensorNetworkPanel,
                consolePanel);
        //create kafka consumer
        kafkaMessageConsumer = new KafkaMessageConsumer(actionPanelController,
                connectedSensorPanelController,
                disconnectedSensorPanelController,
                eventPanelController);
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
