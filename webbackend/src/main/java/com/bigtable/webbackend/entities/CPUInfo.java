/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigtable.webbackend.entities;

/**
 *
 * @author chungnt
 */
public class CPUInfo {

    private double cpuUsage;

    public CPUInfo(double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public double getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

}
