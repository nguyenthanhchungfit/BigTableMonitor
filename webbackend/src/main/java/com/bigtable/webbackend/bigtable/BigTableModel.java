/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigtable.webbackend.bigtable;

import com.bigtable.webbackend.common.BTConfig;
import com.bigtable.webbackend.common.ErrorDefinition;
import com.bigtable.webbackend.entities.CPUInfo;
import com.bigtable.webbackend.entities.MonitorCpuInfo;
import com.bigtable.webbackend.entities.MonitorDataResult;
import com.bigtable.webbackend.entities.MonitorNetworkInfo;
import com.bigtable.webbackend.entities.MonitorRamData;
import com.bigtable.webbackend.entities.NetworkInfo;
import com.bigtable.webbackend.entities.RamInfo;
import com.bigtable.webbackend.utils.JsonUtils;
import com.google.cloud.bigtable.data.v2.models.Query;
import com.google.cloud.bigtable.data.v2.models.Row;
import com.google.cloud.bigtable.data.v2.models.RowCell;
import com.google.cloud.bigtable.data.v2.models.RowMutation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    public boolean initTable() {
        // create table
        List<String> listColumnFamily = Arrays.asList(BTConfig.CF_CPU, BTConfig.CF_NETWORK, BTConfig.CF_RAM);
        if (this._adminClient.createTableIfNotExist(BTConfig.MONITOR_TABLE_ID, listColumnFamily)) {
            _logger.info(String.format("Init big table %s successfully with columns: %s", BTConfig.MONITOR_TABLE_ID, listColumnFamily));
            return true;
        } else {
            _logger.error(String.format("Init big table %s successfully with columns: %s", BTConfig.MONITOR_TABLE_ID, listColumnFamily));
            return false;
        }
    }

    public void submitData(String appName, CPUInfo cpuInfo, NetworkInfo networkInfo, RamInfo ramInfo) {
        long curTime = System.currentTimeMillis();
        String rowKey = formatKey(appName, curTime);
        RowMutation rowMutation = RowMutation.create(BTConfig.MONITOR_TABLE_ID, rowKey)
            .setCell(BTConfig.CF_CPU, BTConfig.CF_QUALIFIER_CPU_USAGE, cpuInfo.getCpuUsage() + "")
            .setCell(BTConfig.CF_NETWORK, BTConfig.CF_QUALIFIER_NETWORK_TRAFFIC, networkInfo.getNetworkUsage() + "")
            .setCell(BTConfig.CF_RAM, BTConfig.CF_QUALIFIER_RAM_HEAP_USAGE, ramInfo.getMemoryHeapUsage() + "")
            .setCell(BTConfig.CF_RAM, BTConfig.CF_QUALIFIER_RAM_NONHEAP_USAGE, ramInfo.getNonHeapUsage() + "");
        _logger.info(">>> Row key: " + rowKey);
        this._dataClient.writeDataToTable(rowMutation);
    }

    public MonitorDataResult queryData(String appName, long fromTime, long toTime) {
        String fromKey = formatKey(appName, fromTime);
        String toKey = formatKey(appName, toTime);
        Query query = Query.create(BTConfig.MONITOR_TABLE_ID)
            .range(fromKey, toKey);
        List<Row> listRows = this._dataClient.readRange(query);
        if (!listRows.isEmpty()) {

            List<Long> listTimestamp = new ArrayList<>();
            List<Double> cpuMetrics = new ArrayList<>();
            List<Long> networkMetrics = new ArrayList<>();
            List<Long> ramHeapMetrics = new ArrayList<>();
            List<Long> ramNonHeapMetrics = new ArrayList<>();
            for (Row row : listRows) {
//                row.getKey().
                long timestamp = 0l;
                System.out.println("key: " + row.getKey().toStringUtf8());
                for (RowCell cell : row.getCells()) {
                    String cfFamily = cell.getFamily();
                    String qualifier = cell.getQualifier().toStringUtf8();
                    String value = cell.getValue().toStringUtf8();
                    timestamp = cell.getTimestamp();
                    if (BTConfig.CF_CPU.equals(cfFamily) && BTConfig.CF_QUALIFIER_CPU_USAGE.equals(qualifier)) {
                        cpuMetrics.add(Double.valueOf(value));
                    } else if (BTConfig.CF_NETWORK.equals(cfFamily) && BTConfig.CF_QUALIFIER_NETWORK_TRAFFIC.equals(qualifier)) {
                        networkMetrics.add(Long.valueOf(value));
                    } else if (BTConfig.CF_RAM.equals(cfFamily)) {
                        if (BTConfig.CF_QUALIFIER_RAM_HEAP_USAGE.equals(qualifier)) {
                            ramHeapMetrics.add(Long.valueOf(value));
                        } else if (BTConfig.CF_QUALIFIER_RAM_NONHEAP_USAGE.equals(qualifier)) {
                            ramNonHeapMetrics.add(Long.valueOf(value));
                        }
                    }
//                    System.out.println("cell:  " + cell.getFamily() + ",  qualifier: "
//                        + cell.getQualifier().toStringUtf8() + ", value: " + cell.getValue().toStringUtf8() + ", timestamp: " + cell.getTimestamp());
                }
                if (timestamp > 0) {
                    timestamp /= 1000;
                }
                listTimestamp.add(timestamp);
            }

            return new MonitorDataResult(0, new MonitorCpuInfo(cpuMetrics), new MonitorNetworkInfo(networkMetrics),
                new MonitorRamData(ramHeapMetrics, ramNonHeapMetrics), listTimestamp);
        }

        return new MonitorDataResult(ErrorDefinition.ERR_PARAMS_RESULT_EMPTY);
    }

    public String formatKey(String appName, long timestamp) {
        return String.format("%s/%d", appName, timestamp);
    }

    public static void main(String[] args) {
        String appName = "app-collector-1";
        INSTANCE.initTable();
        INSTANCE.queryData(appName, 1615131700639l, 1615131753722l);
//        String rowKey = "app-collector-1/1615131380173";
//        Row row = INSTANCE._dataClient.readSingleRow(BTConfig.MONITOR_TABLE_ID, rowKey);
//        for (RowCell cell : row.getCells()) {
//            System.out.printf(
//                "Family: %s    Qualifier: %s    Value: %s%n",
//                cell.getFamily(), cell.getQualifier().toStringUtf8(), cell.getValue().toStringUtf8());
//        }
    }
}
