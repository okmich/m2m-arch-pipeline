/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.sensor.simulator.gui;

import com.okmich.sensor.simulator.FlowStates;
import static com.okmich.sensor.simulator.FlowStates.*;
import static com.okmich.sensor.simulator.OptionRegistry.*;
import com.okmich.sensor.simulator.Status;
import com.okmich.sensor.simulator.model.Reading;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.table.TableModel;

/**
 *
 * @author m.enudi
 */
public class ApplicationFrame extends javax.swing.JFrame implements UserInterface {

    private final ImageIcon[] icons;
    private String simMode = "";
    private int connectionStatus = 0;
    private final String type;

    /**
     * Creates new form ApplicationFrame
     *
     * @param type
     */
    public ApplicationFrame(String type) {
        icons = new ImageIcon[3];
        icons[0] = createImageIcon("/images/red-ball-s.png", "Red ball");
        icons[1] = createImageIcon("/images/green-ball-s.png", "Green ball");
        icons[2] = createImageIcon("/images/gray-ball-s.png", "Gray ball");
        this.type = type;
        initComponents();

        //set the default status icons
        this.setIconLabel(this.connectionStatusLabel, connectionStatus);
        this.setIconLabel(this.flowStatusLabel, connectionStatus);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        basicDisplayPanel = new javax.swing.JPanel();
        statusDisplayPanel = new javax.swing.JPanel();
        connectionStatusLabel = new javax.swing.JLabel();
        flowStatusLabel = new javax.swing.JLabel();
        detailDisplayPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnSimulate = new javax.swing.JButton();
        cbmSimMode = new javax.swing.JComboBox();
        readingPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        readingsTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sensor Simulation");
        setResizable(false);

        connectionStatusLabel.setToolTipText("Connection status");
        connectionStatusLabel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        flowStatusLabel.setToolTipText("Data measurement and transfer status");

        javax.swing.GroupLayout statusDisplayPanelLayout = new javax.swing.GroupLayout(statusDisplayPanel);
        statusDisplayPanel.setLayout(statusDisplayPanelLayout);
        statusDisplayPanelLayout.setHorizontalGroup(
            statusDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusDisplayPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(flowStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(connectionStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );
        statusDisplayPanelLayout.setVerticalGroup(
            statusDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusDisplayPanelLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(connectionStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(flowStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setText("Device ID:");

        jLabel2.setText("Sensor type:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        btnSimulate.setText("Simulate");
        btnSimulate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimulateActionPerformed(evt);
            }
        });

        cbmSimMode.setModel(new javax.swing.DefaultComboBoxModel(new String[] {FlowStates.NORMAL.toString(), TURBULENCE.toString(), LEAKAGE.toString(), DISCONNECTION.toString(), STOPFLOW.toString() }));

        javax.swing.GroupLayout detailDisplayPanelLayout = new javax.swing.GroupLayout(detailDisplayPanel);
        detailDisplayPanel.setLayout(detailDisplayPanelLayout);
        detailDisplayPanelLayout.setHorizontalGroup(
            detailDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailDisplayPanelLayout.createSequentialGroup()
                .addComponent(cbmSimMode, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSimulate, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(detailDisplayPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detailDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(detailDisplayPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(detailDisplayPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        detailDisplayPanelLayout.setVerticalGroup(
            detailDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailDisplayPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detailDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(detailDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(detailDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSimulate, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(cbmSimMode))
                .addContainerGap())
        );

        jLabel3.setText(this.type != null && this.type.equals(Status.STN_TYPE_BASE_STATION) ? "Base station Sensor" : "Pipe data sensor");
        jLabel4.setText(value(DEVICE_ID));

        javax.swing.GroupLayout basicDisplayPanelLayout = new javax.swing.GroupLayout(basicDisplayPanel);
        basicDisplayPanel.setLayout(basicDisplayPanelLayout);
        basicDisplayPanelLayout.setHorizontalGroup(
            basicDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, basicDisplayPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(detailDisplayPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(statusDisplayPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        basicDisplayPanelLayout.setVerticalGroup(
            basicDisplayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusDisplayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(basicDisplayPanelLayout.createSequentialGroup()
                .addComponent(detailDisplayPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        readingPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Readings"));
        readingPanel.setForeground(new java.awt.Color(153, 153, 153));

        readingsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Timestamp", null},
                {"Device ID", null},
                {"Pressure", null},
                {"Temperature", null},
                {"Volume", null},
                {"Flow velocity", null},
                {null, null}
            },
            new String [] {
                "Attribute", "Value"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(readingsTable);
        if (readingsTable.getColumnModel().getColumnCount() > 0) {
            readingsTable.getColumnModel().getColumn(0).setHeaderValue("Attribute");
            readingsTable.getColumnModel().getColumn(1).setResizable(false);
            readingsTable.getColumnModel().getColumn(1).setHeaderValue("Value");
        }

        javax.swing.GroupLayout readingPanelLayout = new javax.swing.GroupLayout(readingPanel);
        readingPanel.setLayout(readingPanelLayout);
        readingPanelLayout.setHorizontalGroup(
            readingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        readingPanelLayout.setVerticalGroup(
            readingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(readingPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(basicDisplayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(readingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(basicDisplayPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(readingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimulateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimulateActionPerformed
        String mode = cbmSimMode.getSelectedItem().toString();
        if (mode.equalsIgnoreCase("stopflow")) {
            cbmSimMode.setSelectedIndex(0);
            return;
        }
        //handle disconnection
        if (mode.equalsIgnoreCase(DISCONNECTION.toString())) {
            setConnectionStatus(Status.STATUS_OFF);
        } else {
            setConnectionStatus(Status.STATUS_ON);
        }

        setMode(this.cbmSimMode.getSelectedItem().toString());
        this.setTitle("Sensor Simulation (" + this.cbmSimMode.getSelectedItem().toString() + ")");
    }//GEN-LAST:event_btnSimulateActionPerformed

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    private ImageIcon createImageIcon(String path,
            String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    @Override
    public String getMode() {
        return this.simMode;
    }

    @Override
    public void setMode(String mode) {
        if (isDisconnectedMode()) {
            return;
        }
        this.simMode = mode;
        cbmSimMode.setSelectedItem(mode);
        this.setTitle("Sensor Simulation (" + this.simMode + ")");
        //
        if (mode.equals(STOPFLOW.toString())) {
            setFlowStatus(Status.STATUS_NO_FLOW);
        }
    }

    @Override
    public boolean isDisconnectedMode() {
        return this.connectionStatus == Status.STATUS_OFF;
    }

    private void setIconLabel(JLabel label, int i) {
        label.setText("");
        label.setIcon(this.icons[i]);
    }

    /**
     *
     * @param data
     */
    @Override
    public void refreshTableData(Reading data) {
        if (data == null) {
            return;
        }
        TableModel tableModel = readingsTable.getModel();
        //timestamp
        tableModel.setValueAt("Timestamp", 0, 0);
        tableModel.setValueAt(new Date(data.getTimestamp()).toString(), 0, 1);
        //dev id
        tableModel.setValueAt("Device ID", 1, 0);
        tableModel.setValueAt(data.getDevId(), 1, 1);
        //Pressure
        tableModel.setValueAt("Pressure (n/m2)", 2, 0);
        tableModel.setValueAt(data.getPressure(), 2, 1);
        //Temperature (deg Celsius)
        tableModel.setValueAt("Temperature (deg Celsius)", 3, 0);
        tableModel.setValueAt(data.getTemperature(), 3, 1);
        //Volume (cubic metre per height)
        tableModel.setValueAt("Volume (cubic metre per height)", 4, 0);
        tableModel.setValueAt(data.getVolume(), 4, 1);
        //Flow velocity (metre per second)
        tableModel.setValueAt("Flow velocity (metre per second)", 5, 0);
        tableModel.setValueAt(data.getFlowVelocity(), 5, 1);
        //Ext. body force (n)
        tableModel.setValueAt("Ext. body force (n)", 6, 0);
        tableModel.setValueAt(data.getExtBodyForce(), 6, 1);
    }

    @Override
    public void setConnectionStatus(int connStatus) {
        this.connectionStatus = connStatus;
        connectionStatusLabel.setIcon(this.icons[connStatus]);
    }

    @Override
    public int getFlowStatus() {
        if (this.simMode.equals(STOPFLOW.toString())) {
            return Status.STATUS_NO_FLOW;
        } else if (this.simMode.equalsIgnoreCase(DISCONNECTION.toString())) {
            return Status.STATUS_OFF;
        } else {
            return Status.STATUS_ON;
        }
    }

    @Override
    public void setFlowStatus(int connStatus) {
        flowStatusLabel.setIcon(this.icons[connStatus]);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel basicDisplayPanel;
    private javax.swing.JButton btnSimulate;
    private javax.swing.JComboBox cbmSimMode;
    private javax.swing.JLabel connectionStatusLabel;
    private javax.swing.JPanel detailDisplayPanel;
    private javax.swing.JLabel flowStatusLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel readingPanel;
    private javax.swing.JTable readingsTable;
    private javax.swing.JPanel statusDisplayPanel;
    // End of variables declaration//GEN-END:variables

}
