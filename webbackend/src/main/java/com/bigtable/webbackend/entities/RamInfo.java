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
public class RamInfo {

    private long memoryHeapUsage;
    private long nonHeapUsage;

    public RamInfo(long memoryHeapUsage, long nonHeapUsage) {
        this.memoryHeapUsage = memoryHeapUsage;
        this.nonHeapUsage = nonHeapUsage;
    }

    public long getMemoryHeapUsage() {
        return memoryHeapUsage;
    }

    public void setMemoryHeapUsage(long memoryHeapUsage) {
        this.memoryHeapUsage = memoryHeapUsage;
    }

    public long getNonHeapUsage() {
        return nonHeapUsage;
    }

    public void setNonHeapUsage(long nonHeapUsage) {
        this.nonHeapUsage = nonHeapUsage;
    }

}
