/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard.views;

import com.okmich.m2m.backoffice.dashboard.views.tablemodel.DefaultTableModel;
import java.util.List;

/**
 *
 * @author ABME340
 */
public class EventPanel extends javax.swing.JPanel implements UIView<String[]> {

    private final DefaultTableModel tableModel;

    /**
     * Creates new form FeedStatusPanel
     */
    public EventPanel() {
        tableModel = new DefaultTableModel(new String[]{"Sensor", "Event", "...", "Time"});
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        eventTable = new javax.swing.JTable();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Detected Event"));
        setPreferredSize(new java.awt.Dimension(350, 424));
        setLayout(new java.awt.GridLayout(1, 0));

        eventTable.setModel(tableModel);
        eventTable.setEnabled(false);
        jScrollPane3.setViewportView(eventTable);

        add(jScrollPane3);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable eventTable;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables

    @Override
    public void refreshData(List<String[]> tList) {
        for (String[] data : tList) {
            this.tableModel.add(data);
        }
    }

    @Override
    public void refreshData(String[] datat) {
        this.tableModel.add(datat);
    }
}
