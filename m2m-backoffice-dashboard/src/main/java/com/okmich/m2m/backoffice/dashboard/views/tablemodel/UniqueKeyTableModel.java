/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard.views.tablemodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author datadev
 */
public class UniqueKeyTableModel extends AbstractTableModel {

    private final String[] columnNames;
    private final List<String[]> dataset;
    private final Map<String, Integer> indexMap;

    private static final Logger LOG = Logger.getLogger(UniqueKeyTableModel.class.getName());

    /**
     *
     * @param columnNames
     */
    public UniqueKeyTableModel(String[] columnNames) {
        this.columnNames = columnNames;
        this.dataset = new ArrayList<>();
        indexMap = new HashMap<>();
    }

    @Override
    public int getRowCount() {
        return dataset.size();
    }

//    @Override
//    public Class getColumnClass(int i) {
//        switch (i) {
//            case 0:
//                return Integer.class;
//            case 1:
//            case 2:
//            case 4:
//            case 5:
//            case 6:
//                return Float.class;
//            default:
//                return String.class;
//        }
//    }

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

    public void add(String key, String[] data) {
        if (indexMap.containsKey(key)) {
            this.dataset.set(indexMap.get(key), data);
        } else {
            if (this.dataset.add(data)) {
                indexMap.put(key, this.dataset.size() - 1);
            }
        }
        try {
            this.fireTableDataChanged();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void remove(String key) {
        if (indexMap.containsKey(key)) {
            int index = indexMap.get(key);
            dataset.remove(index);
            indexMap.values().stream().forEach((Integer t) -> {
                if (t > index) {
                    --t;
                }
            });
        }
        try {
            this.fireTableDataChanged();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
