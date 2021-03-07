/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigtable.webbackend.models;

import com.bigtable.webbackend.bigtable.BigTableModel;
import com.bigtable.webbackend.entities.CPUInfo;
import com.bigtable.webbackend.entities.NetworkInfo;
import com.bigtable.webbackend.entities.RamInfo;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.util.Random;

/**
 *
 * @author chungnt
 */
public class CollectorsModel {

    private static final Random rd = new Random(10);

    public static void collectInfo(String appName) {
        RamInfo ramInfo = collectRamInfo();
        CPUInfo cpuInfo = collectCPUInfo();
        NetworkInfo networkInfo = collectNetworkInfo();

        // submit data
        BigTableModel.INSTANCE.submitData(appName, cpuInfo, networkInfo, ramInfo);
    }

    public static RamInfo collectRamInfo() {
        ManagementFactory.getThreadMXBean().getTotalStartedThreadCount();

        MemoryUsage heap = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        MemoryUsage nonHeap = ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage();

        RamInfo ramInfo = new RamInfo(heap.getUsed(), nonHeap.getUsed());
        heap.getUsed();
        nonHeap.getUsed();
        return ramInfo;
    }

    public static CPUInfo collectCPUInfo() {
        int random = rd.nextInt(100);
        double cpuUsage = random * 1.0 / 100;
        CPUInfo cpuInfo = new CPUInfo(cpuUsage);
        return cpuInfo;
    }

    public static NetworkInfo collectNetworkInfo() {
        long network = rd.nextInt(100);
        long networkUsage = (long) (network * 1.0 * 10000 / 100);
        NetworkInfo networkInfo = new NetworkInfo(networkUsage);
        return networkInfo;
    }
}
