/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard.views;

import static com.okmich.m2m.backoffice.dashboard.OptionRegistry.*;
import com.okmich.m2m.backoffice.dashboard.model.SensorReading;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;
import java.util.List;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.DialBackground;
import org.jfree.chart.plot.dial.DialCap;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialTextAnnotation;
import org.jfree.chart.plot.dial.DialValueIndicator;
import org.jfree.chart.plot.dial.StandardDialFrame;
import org.jfree.chart.plot.dial.StandardDialRange;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;

/**
 *
 * @author m.enudi
 */
public class SourceProductionChartPanel extends ChartPanel implements UIView<SensorReading> {

    private static DefaultValueDataset dailyDs;
    private static DefaultValueDataset instantDs;

    /**
     *
     */
    public SourceProductionChartPanel() {
        super(createSourceProductionChart());
    }

    private static JFreeChart createSourceProductionChart() {
        dailyDs = new DefaultValueDataset(0D);
        instantDs = new DefaultValueDataset(0.5);

        DialPlot dialplot = new DialPlot();

        dialplot.setView(0.0D, 0.0D, 1.0D, 1.0D);
        dialplot.setDataset(0, dailyDs);
        dialplot.setDataset(1, instantDs);

        StandardDialFrame standarddialframe = new StandardDialFrame();
        standarddialframe.setBackgroundPaint(Color.lightGray);
        standarddialframe.setForegroundPaint(Color.darkGray);
        dialplot.setDialFrame(standarddialframe);

        GradientPaint gradientpaint = new GradientPaint(new Point(), new Color(255, 255, 255), new Point(), new Color(170, 170, 220));
        DialBackground dialbackground = new DialBackground(gradientpaint);

        dialbackground.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL));
        dialplot.setBackground(dialbackground);

        DialTextAnnotation dialtextannotation = new DialTextAnnotation("Temperature");
        dialtextannotation.setFont(new Font("Dialog", 1, 14));
        dialtextannotation.setRadius(0.69999999999999996D);
        dialplot.addLayer(dialtextannotation);

        DialValueIndicator dialvalueindicator = new DialValueIndicator(0);
        dialvalueindicator.setFont(new Font("Dialog", 0, 10));
        dialvalueindicator.setOutlinePaint(Color.darkGray);
        dialvalueindicator.setRadius(0.59999999999999998D);
        dialvalueindicator.setAngle(-103D);
        dialplot.addLayer(dialvalueindicator);

        DialValueIndicator dialvalueindicator1 = new DialValueIndicator(1);
        dialvalueindicator1.setFont(new Font("Dialog", 0, 10));
        dialvalueindicator1.setOutlinePaint(Color.red);
        dialvalueindicator1.setRadius(0.59999999999999998D);
        dialvalueindicator1.setAngle(-77D);
        dialplot.addLayer(dialvalueindicator1);

        StandardDialScale standarddialscale = new StandardDialScale(-40D, 60D, -120D, -300D, 10D, 4);
        standarddialscale.setTickRadius(0.88D);
        standarddialscale.setTickLabelOffset(0.14999999999999999D);
        standarddialscale.setTickLabelFont(new Font("Dialog", 0, 14));
        dialplot.addScale(0, standarddialscale);

        StandardDialScale standarddialscale1 = new StandardDialScale(0.0D, 100D, -90D, -270D, 10D, 4);
        standarddialscale1.setTickRadius(0.5D);
        standarddialscale1.setTickLabelOffset(0.14999999999999999D);
        standarddialscale1.setTickLabelFont(new Font("Dialog", 0, 10));
        standarddialscale1.setMajorTickPaint(Color.red);
        standarddialscale1.setMinorTickPaint(Color.red);
        dialplot.addScale(1, standarddialscale1);

        dialplot.mapDatasetToScale(0, 0);

        StandardDialRange standarddialrange = new StandardDialRange(90D, 100D, Color.blue);
        standarddialrange.setScaleIndex(1);
        standarddialrange.setInnerRadius(0.58999999999999997D);
        standarddialrange.setOuterRadius(0.58999999999999997D);
        dialplot.addLayer(standarddialrange);

        org.jfree.chart.plot.dial.DialPointer.Pin pin = new org.jfree.chart.plot.dial.DialPointer.Pin(1);
        pin.setRadius(0.55000000000000004D);
        dialplot.addPointer(pin);

        org.jfree.chart.plot.dial.DialPointer.Pointer pointer = new org.jfree.chart.plot.dial.DialPointer.Pointer(0);
        dialplot.addPointer(pointer);

        DialCap dialcap = new DialCap();
        dialcap.setRadius(0.10000000000000001D);
        dialplot.setCap(dialcap);

        JFreeChart chart = new JFreeChart(dialplot);
        chart.setTitle(new TextTitle("Daily production at source - (Sensor " + value(SOURCE_SENSOR_ID) + ")",
                new Font("Serif", java.awt.Font.BOLD, 16)));

        return chart;
    }

    @Override
    public void refreshData(List<SensorReading> tList) {

    }

    @Override
    public void refreshData(SensorReading t) {
        dailyDs.setValue(t.getCapacity());
        instantDs.setValue(t.getFlowVelocity());
    }
}
