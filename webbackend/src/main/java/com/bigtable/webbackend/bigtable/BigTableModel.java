/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigtable.webbackend.bigtable;

import com.bigtable.webbackend.common.BigTableConfiguration;
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
        String projectId = BigTableConfiguration.MONITOR_PROJECT_ID;
        String instanceId = BigTableConfiguration.MONITOR_INSTANCE_ID;
        _adminClient = new BigtableAdminClientWrapper(projectId, instanceId);
        _dataClient = new BigtableDataClientWrapper(projectId, instanceId);
    }

    public void initTable() {
        // create table

    }

    public static void main(String[] args) {

    }
}
