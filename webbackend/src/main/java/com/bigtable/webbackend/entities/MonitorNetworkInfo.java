/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigtable.webbackend.entities;

import java.util.List;

/**
 *
 * @author chungnt
 */
public class MonitorNetworkInfo {

    private List<Long> trafficMetrics;

    public MonitorNetworkInfo() {
    }

    public MonitorNetworkInfo(List<Long> trafficMetrics) {
        this.trafficMetrics = trafficMetrics;
    }

    public List<Long> getTrafficMetrics() {
        return trafficMetrics;
    }

    public void setTrafficMetrics(List<Long> trafficMetrics) {
        this.trafficMetrics = trafficMetrics;
    }
}
