/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard.views;

import java.awt.Font;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.AreaRendererEndType;
import org.jfree.chart.renderer.category.AreaRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;

/**
 *
 * @author ABME340
 */
public class SourceProductionChartPanel extends ChartPanel implements UIView<String[]> {
    
    /**
     *
     */
    public SourceProductionChartPanel(){
        super(createSourceProductionChart());
    }
    
    
    private static JFreeChart createSourceProductionChart() {
        double[][] data = new double[][]{
            {82502, 84026, 85007, 86216, 85559, 84491, 87672,
                88575, 89837, 90701}
        };

        CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
                new String[]{"Oil"}, new String[]{"2004", "2005", "2006",
                    "2007", "2008", "2009", "2010", "2011", "2012", "2013"},
                data
        );
        
        JFreeChart chart = ChartFactory.createAreaChart(
                "Source production",
                "Time",
                "Cubic/minute",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                true
        );

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setForegroundAlpha(0.3f);

        AreaRenderer renderer = (AreaRenderer) plot.getRenderer();
        renderer.setEndType(AreaRendererEndType.LEVEL);

        chart.setTitle(new TextTitle("Source production",
                new Font("Serif", java.awt.Font.BOLD, 18))
        );

        return chart;
    }

    @Override
    public void refreshData(List<String[]> tList) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
