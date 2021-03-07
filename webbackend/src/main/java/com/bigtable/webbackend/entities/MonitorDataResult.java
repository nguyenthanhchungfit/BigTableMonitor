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
public class MonitorDataResult {

    private int error;
    private MonitorCpuInfo cpuInfo;
    private MonitorNetworkInfo networkInfo;
    private MonitorRamData ramInfo;
    private List<Long> timestamps;

    public MonitorDataResult() {
    }

    public MonitorDataResult(int error, MonitorCpuInfo cpuInfo, MonitorNetworkInfo networkInfo, MonitorRamData ramInfo, List<Long> timestamps) {
        this.error = error;
        this.cpuInfo = cpuInfo;
        this.networkInfo = networkInfo;
        this.ramInfo = ramInfo;
        this.timestamps = timestamps;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public MonitorCpuInfo getCpuInfo() {
        return cpuInfo;
    }

    public void setCpuInfo(MonitorCpuInfo cpuInfo) {
        this.cpuInfo = cpuInfo;
    }

    public MonitorNetworkInfo getNetworkInfo() {
        return networkInfo;
    }

    public void setNetworkInfo(MonitorNetworkInfo networkInfo) {
        this.networkInfo = networkInfo;
    }

    public MonitorRamData getRamInfo() {
        return ramInfo;
    }

    public void setRamInfo(MonitorRamData ramInfo) {
        this.ramInfo = ramInfo;
    }

    public List<Long> getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(List<Long> timestamps) {
        this.timestamps = timestamps;
    }

}
