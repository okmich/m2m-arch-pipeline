/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard;

import com.okmich.m2m.backoffice.dashboard.model.Sensor;
import static com.okmich.m2m.backoffice.dashboard.model.Sensor.STATUS_ACTIVE;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author m.enudi
 */
public class SensorRegistry {

    private Graph<Sensor, String> sensorNetwork;

    private List<Sensor> sensors;

    static final String records = "00001;bs;sensor descritpion along the pipeline;;;0;lat,lon\n"
            + "00002;ns;00002 address descritpion;00001;00001;30;lat,lon\n"
            + "00003;bs;address descritpion;00001;00001;30;lat,lon\n"
            + "00004;ns;00004 address descritpion;00001;00001;30;lat,lon\n"
            + "00005;ns;00005 address descritpion;00002;00001;50;lat,lon\n"
            + "00006;ns;00006 address descritpion;00003;00003;30;lat,lon\n"
            + "00007;ns;00007 address descritpion;00006;00003;60;lat,lon\n"
            + "00008;bs;00008 address descritpion;00003;00003;30;lat,lon\n"
            + "00009;ns;00009 sensor address descritpion;00008;00008;30;lat,lon\n"
            + "00010;bs;0001o physical address descritpion;00004;00001;60;lat,lon\n"
            + "00011;ns;address descritpion;00010;00010;30;lat,lon\n"
            + "00012;ns;snsor 00012 address;00010;00010;30;lat,lon\n"
            + "00013;ns;sensor 00013 physical address;00011;00010;50;lat,lon\n"
            + "00014;ns;00014 address;00013;00010;70;lat,lon";

    public SensorRegistry() {
        String[] sensorString = records.split("\\n");
        this.sensors = new ArrayList<>(sensorString.length);
        for (String each : sensorString) {
            sensors.add(Sensor.valueOf(each));
        }

        this.sensorNetwork = new DirectedSparseGraph<>();
        for (Sensor s : sensors) {
            sensorNetwork.addVertex(s);
        }
        for (Sensor s : sensors) {
            if (s.getSupplyDevId() != null) {
                sensorNetwork.addEdge(s.getDevId(), new Sensor(s.getSupplyDevId()), s);
            }
        }
    }

    public static void main(String[] args) {
        SensorRegistry sensorRegistry = new SensorRegistry();

        Layout<Sensor, String> layout = new ISOMLayout<>(sensorRegistry.sensorNetwork);
        //layout.setSize(new Dimension(600, 600));
        VisualizationViewer<Sensor, String> vv = new VisualizationViewer<>(layout);
        //vv.setPreferredSize(new Dimension(650, 650));
        // Show vertex and edge labels
        vv.getRenderContext().setVertexLabelTransformer((Sensor f) -> f.getDevId());
        vv.getRenderContext().setVertexShapeTransformer((Sensor f) -> {
            double width = f.getDevId().length() * 10.0;
            if ("ns".equals(f.getType())) {
                return new Ellipse2D.Double(-(width / 2), -12.5, width, 25);
            } else {
                return new Rectangle2D.Double(-(width / 2), -12.5, width, 25);
            }
        });
        vv.getRenderContext().setEdgeLabelTransformer((String f) -> "");
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);
        // Create a graph mouse and add it to the visualization component
        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        vv.setGraphMouse(gm);
        // Add the mouses mode key listener to work it needs to be added to the visualization component
        vv.addKeyListener(gm.getModeKeyListener());
        JFrame frame = new JFrame("Interactive Graph View 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);

        try {
            Thread.currentThread().sleep(2000);
            for (Sensor s : sensorRegistry.getSensors()) {
                s.setStatus(STATUS_ACTIVE);
                Thread.currentThread().sleep(1000);
                vv.repaint();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(SensorRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @return the sensors
     */
    public List<Sensor> getSensors() {
        return sensors;
    }

}
