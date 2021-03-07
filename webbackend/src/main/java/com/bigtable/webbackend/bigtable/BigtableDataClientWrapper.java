/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigtable.webbackend.bigtable;

import com.google.cloud.bigtable.data.v2.BigtableDataClient;
import com.google.cloud.bigtable.data.v2.BigtableDataSettings;
import com.google.cloud.bigtable.data.v2.models.Query;
import com.google.cloud.bigtable.data.v2.models.Row;
import com.google.cloud.bigtable.data.v2.models.RowCell;
import com.google.cloud.bigtable.data.v2.models.RowMutation;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author chungnt
 */
public class BigtableDataClientWrapper {

    private String _projectId;
    private String _instanceId;
    private BigtableDataClient _dataClient;

    private static final Logger _logger = LogManager.getLogger(BigtableDataClientWrapper.class);

    public BigtableDataClientWrapper(String projectId, String instanceId) {
        this._projectId = projectId;
        this._instanceId = instanceId;

        BigtableDataSettings settings = BigtableDataSettings.newBuilder().setProjectId(_projectId).setInstanceId(_instanceId).build();
        try {
            _dataClient = BigtableDataClient.create(settings);
        } catch (IOException ex) {
            _logger.error(ex.getMessage(), ex);
        }
    }

    // read 
    public Row readSingleRow(String tableId, String rowKey) {
        return this._dataClient.readRow(tableId, rowKey);
    }

    public List<RowCell> readSpecificCells(String tableId, String rowKey, String clFamily, String clQualifierName) {
        Row row = readSingleRow(tableId, rowKey);
        List<RowCell> cells = row.getCells(clFamily, clQualifierName);
        return cells;
    }

    public void writeDataToTable(String tableId, RowMutation rowMutation) {
        this._dataClient.mutateRow(rowMutation);
    }

    // write
}
