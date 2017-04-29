/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard.views.tablemodel;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author datadev
 */
public class DefaultTableModel extends AbstractTableModel {

    private final String[] columnNames;
    private final List<String[]> dataset;

    private static final Logger LOG = Logger.getLogger(DefaultTableModel.class.getName());

    /**
     * 
     * @param columnNames 
     */
    public DefaultTableModel(String[]  columnNames) {
        this.columnNames = columnNames;
        this.dataset = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return dataset.size();
    }

 

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int i) {
        return columnNames[i];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String[] row = this.dataset.get(rowIndex);
        if (row != null) {
            return row[columnIndex];
        }
        return null;
    }

    public void add(String[] data) {
       this.dataset.add(data);
        try {
            this.fireTableDataChanged();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
