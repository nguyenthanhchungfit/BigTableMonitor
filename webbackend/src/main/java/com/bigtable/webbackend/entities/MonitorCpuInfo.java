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
public class MonitorCpuInfo {
    private List<Double> cpuUsageMetrics;

    public MonitorCpuInfo() {
    }

    
    public MonitorCpuInfo(List<Double> cpuUsageMetrics) {
        this.cpuUsageMetrics = cpuUsageMetrics;
    }

    public List<Double> getCpuUsageMetrics() {
        return cpuUsageMetrics;
    }

    public void setCpuUsageMetrics(List<Double> cpuUsageMetrics) {
        this.cpuUsageMetrics = cpuUsageMetrics;
    }
}
