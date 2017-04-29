/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard;

import com.okmich.m2m.backoffice.dashboard.views.ActionPanel;
import com.okmich.m2m.backoffice.dashboard.views.DashboardPanel;
import com.okmich.m2m.backoffice.dashboard.views.ConsolePanel;
import com.okmich.m2m.backoffice.dashboard.views.EventPanel;
import com.okmich.m2m.backoffice.dashboard.views.SourceProductionChartPanel;
import com.okmich.m2m.backoffice.dashboard.views.NetworkStatusDistPanel;
import com.okmich.m2m.backoffice.dashboard.views.SensorPanel;
import com.okmich.m2m.backoffice.dashboard.views.SensorNetworkPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JPanel;

/**
 *
 * @author ABME340
 */
public class MainGUIFrame extends javax.swing.JFrame {

    /**
     *
     * @param sensorPanel
     * @param eventPanel
     * @param actionPanel
     * @param sourceProductionChartPanel
     * @param networkStatusDistPanel
     * @param sensorNetworkPanel
     * @param consolePanel
     */
    public MainGUIFrame(SensorPanel sensorPanel,
            EventPanel eventPanel,
            ActionPanel actionPanel,
            SourceProductionChartPanel sourceProductionChartPanel,
            NetworkStatusDistPanel networkStatusDistPanel,
            SensorNetworkPanel sensorNetworkPanel,
            ConsolePanel consolePanel) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            throw new RuntimeException(ex);
        }

        initComponents();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> after initComponents");
        JPanel eventActionPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        //decorate the panel
        eventActionPanel.add(eventPanel);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> after eventActionPanel.add(new EventPanel())");
        eventActionPanel.add(actionPanel);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> after eventActionPanel.add(new ActionPanel())");

        getContentPane().add(consolePanel, BorderLayout.SOUTH);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> after getContentPane().add(new ConsolePanel(), BorderLayout.SOUTH)");
        getContentPane().add(new DashboardPanel(), BorderLayout.NORTH);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> after getContentPane().add(new DashboardPanel(), BorderLayout.NORTH)");
        getContentPane().add(eventActionPanel, BorderLayout.EAST);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> after getContentPane().add(new eventActionPanel(), BorderLayout.EAST)");
//        JPanel feedPanel = new JPanel(new GridLayout(2, 1, 5, 5));
//        feedPanel.add(connectedSensorPanel);
//        feedPanel.add(disconnectedSensorPanel);

        getContentPane().add(sensorPanel, BorderLayout.WEST);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> after getContentPane().add(new FeedStatusPanel(), BorderLayout.WEST)");

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        centerPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 102), 1, true));
        centerPanel.add(sensorNetworkPanel);

        JPanel centerBottomPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        centerBottomPanel.add(sourceProductionChartPanel);
        centerBottomPanel.add(networkStatusDistPanel);

        centerPanel.add(centerBottomPanel);

        getContentPane().add(centerPanel, BorderLayout.CENTER);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> after getContentPane().add(new SensorNetworkPanel(), BorderLayout.CENTER)");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }

        });

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> after addWindowListener");
        pack();

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>> after pack");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mnuItemAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SmartPipeNet Console");
        getContentPane().setLayout(new java.awt.BorderLayout(5, 5));

        jMenu1.setText("File");

        jMenuItem1.setText("Exit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Help");

        mnuItemAbout.setText("About");
        jMenu2.add(mnuItemAbout);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem mnuItemAbout;
    // End of variables declaration//GEN-END:variables
}
