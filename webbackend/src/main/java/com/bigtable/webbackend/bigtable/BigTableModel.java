/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigtable.webbackend.bigtable;

import com.bigtable.webbackend.common.BTConfig;
import com.bigtable.webbackend.entities.CPUInfo;
import com.bigtable.webbackend.entities.NetworkInfo;
import com.bigtable.webbackend.entities.RamInfo;
import com.google.cloud.bigtable.data.v2.models.RowMutation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author chungnt
 */
public class BigTableModel {
    
    public static final BigTableModel INSTANCE = new BigTableModel();
    private static final Logger _logger = LogManager.getLogger(BigTableModel.class);
    private BigtableAdminClientWrapper _adminClient;
    private BigtableDataClientWrapper _dataClient;
    
    public BigTableModel() {
        String projectId = BTConfig.MONITOR_PROJECT_ID;
        String instanceId = BTConfig.MONITOR_INSTANCE_ID;
        _adminClient = new BigtableAdminClientWrapper(projectId, instanceId);
        _dataClient = new BigtableDataClientWrapper(projectId, instanceId);
    }
    
    public void initTable() {
        // create table

    }
    
    public void submitData(String appName, CPUInfo cpuInfo, NetworkInfo networkInfo, RamInfo ramInfo) {
        long curTime = System.currentTimeMillis();
        String rowKey = formatKey(appName, curTime);
        RowMutation rowMutation = RowMutation.create(BTConfig.MONITOR_TABLE_ID, rowKey)
            .setCell(BTConfig.CF_CPU, BTConfig.CF_QUALIFIER_CPU_USAGE, cpuInfo.getCpuUsage() + "")
            .setCell(BTConfig.CF_NETWORK, BTConfig.CF_QUALIFIER_NETWORK_TRAFFIC, networkInfo.getNetworkUsage() + "")
            .setCell(BTConfig.CF_RAM, BTConfig.CF_QUALIFIER_RAM_HEAP_USAGE, ramInfo.getMemoryHeapUsage() + "")
            .setCell(BTConfig.CF_RAM, BTConfig.CF_QUALIFIER_RAM_NONHEAP_USAGE, ramInfo.getNonHeapUsage() + "");
        this._dataClient.writeDataToTable(rowMutation);
    }
    
    public String formatKey(String appName, long timestamp) {
        return String.format("%s/%d", appName, timestamp);
    }
    
    public static void main(String[] args) {
        
    }
}
