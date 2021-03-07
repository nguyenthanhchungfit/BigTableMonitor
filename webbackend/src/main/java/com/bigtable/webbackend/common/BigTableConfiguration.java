/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigtable.webbackend.common;

import java.io.File;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ini4j.Wini;

/**
 *
 * @author chungnt
 */
public class BigTableConfiguration {

    public static final String MONITOR_PROJECT_ID;
    public static final String MONITOR_INSTANCE_ID;
    public static final String MONITOR_TABLE_ID;

    public static final String CF_RAM = "ram";
    public static final String CF_CPU = "cpu";
    public static final String CF_NETWORK = "network";

    private static final Logger _logger = LogManager.getLogger(BigTableConfiguration.class);

    static {
        String projectId = "";
        String instanceId = "";
        String monitorTableId = "";
        try {
            Wini iniReader = new Wini(new File("conf/conf.ini"));
            projectId = iniReader.get("bigtable", "projectId");
            instanceId = iniReader.get("bigtable", "instanceId");
            monitorTableId = iniReader.get("bigtable", "monitor-table-id");

        } catch (IOException ex) {
            _logger.error(ex.getMessage(), ex);
        } finally {
            if (StringUtils.isBlank(projectId)) {
                projectId = "vivid-kite-305810"; // default
            }
            if (StringUtils.isBlank(instanceId)) {
                instanceId = "monitor-services";
            }
            if (StringUtils.isBlank(monitorTableId)) {
                monitorTableId = "monitor-timeseries";
            }
            MONITOR_PROJECT_ID = projectId;
            MONITOR_INSTANCE_ID = instanceId;
            MONITOR_TABLE_ID = monitorTableId;
        }

    }

}
