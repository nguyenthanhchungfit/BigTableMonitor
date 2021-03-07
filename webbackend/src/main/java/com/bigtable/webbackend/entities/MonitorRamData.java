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
public class MonitorRamData {

    private List<Long> heapUsageMetrics;
    private List<Long> nonHeapUsageMetrics;

    public MonitorRamData() {
    }

    public MonitorRamData(List<Long> heapUsageMetrics, List<Long> nonHeapUsageMetrics) {
        this.heapUsageMetrics = heapUsageMetrics;
        this.nonHeapUsageMetrics = nonHeapUsageMetrics;
    }

    public List<Long> getHeapUsageMetrics() {
        return heapUsageMetrics;
    }

    public void setHeapUsageMetrics(List<Long> heapUsageMetrics) {
        this.heapUsageMetrics = heapUsageMetrics;
    }

    public List<Long> getNonHeapUsageMetrics() {
        return nonHeapUsageMetrics;
    }

    public void setNonHeapUsageMetrics(List<Long> nonHeapUsageMetrics) {
        this.nonHeapUsageMetrics = nonHeapUsageMetrics;
    }

}
