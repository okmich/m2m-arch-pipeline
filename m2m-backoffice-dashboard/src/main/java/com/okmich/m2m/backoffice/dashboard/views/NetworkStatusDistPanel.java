/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard.views;

import java.awt.Font;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author ABME340
 */
public class NetworkStatusDistPanel extends ChartPanel {

    public NetworkStatusDistPanel() {
        super(createJFreeChart());
    }
    
    
    
    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     *
     * @return A panel.
     */
    public static JFreeChart createJFreeChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Active", new Double(5));
        dataset.setValue("Inactive", new Double(2));
        dataset.setValue("Stale", new Double(3));

        JFreeChart chart = ChartFactory.createPieChart(
                "", // chart title
                dataset, // data
                true, // include legend
                true,
                false
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        plot.setNoDataMessage("No data available");
        plot.setCircular(false);
        plot.setLabelGap(0.02);
        
        chart.setTitle(new TextTitle("Sensor network status distribution",
                new Font("Serif", java.awt.Font.BOLD, 18)));

        return chart;
    }

}
