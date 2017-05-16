/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard.views;

import com.okmich.m2m.backoffice.dashboard.db.CacheService;
import com.okmich.m2m.backoffice.dashboard.model.Sensor;
import com.okmich.m2m.backoffice.dashboard.model.SensorEvent;
import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author m.enudi
 */
public final class NetworkStatusDistPanel extends ChartPanel implements UIView<SensorEvent> {

    private static DefaultPieDataset dataset;
    private final Map<String, String> sensors = new HashMap<>();

    public NetworkStatusDistPanel(CacheService cacheService) {
        super(createJFreeChart());
        for (Sensor s : cacheService.getSensors()) {
            this.sensors.put(s.getDevId(), Sensor.STATUS_INACTIVE);
        }
    }

    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     *
     * @return A panel.
     */
    public static JFreeChart createJFreeChart() {
        dataset = new DefaultPieDataset();

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

    @Override
    public void refreshData(List<SensorEvent> tList) {
        tList.stream().forEach((SensorEvent s) -> refreshData(s));
    }

    @Override
    public void refreshData(SensorEvent t) {
        this.sensors.put(t.getDevId(), t.getStatus());
        reloadDataset();
        System.out.println(">>>>>>>>>>>>>>>>>>>> " + this.sensors);
    }

    private void reloadDataset() {
        Map<String, Long> groupting = this.sensors.keySet().stream()
                .map((String t) -> this.sensors.get(t))
                .map((String s) -> getLabel(s))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        groupting.keySet().stream().forEach((key) -> {
            dataset.setValue(key, groupting.get(key));
        });
        repaint();
    }

    /**
     *
     * @param s
     * @return
     */
    private String getLabel(String s) {
        switch (s) {
            case Sensor.STATUS_ACTIVE:
                return "Connected";
            case Sensor.STATUS_INACTIVE:
                return "Disconnected";
            default:
                return "Stale";
        }
    }
}
