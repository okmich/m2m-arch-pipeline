/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.m2m.backoffice.dashboard.views;

import java.util.List;

/**
 *
 * @author ABME340
 */
public interface UIView<T> {
    
    void refreshData(T t);
    
    void refreshData(List<T> tList);
    
}
