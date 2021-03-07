/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigtable.webbackend.bigtable;

import com.google.cloud.bigtable.admin.v2.BigtableTableAdminClient;
import com.google.cloud.bigtable.admin.v2.BigtableTableAdminSettings;
import com.google.cloud.bigtable.admin.v2.models.CreateTableRequest;
import com.google.cloud.bigtable.admin.v2.models.Table;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author chungnt
 */
public class BigtableAdminClientWrapper {

    private String _projectId;
    private String _instanceId;
    private BigtableTableAdminClient adminClient;

    private static final Logger _logger = LogManager.getLogger(BigtableAdminClientWrapper.class);

    public BigtableAdminClientWrapper(String projectId, String instanceId) {
        this._projectId = projectId;
        this._instanceId = instanceId;

        BigtableTableAdminSettings adminSettings;
        try {
            adminSettings = BigtableTableAdminSettings.newBuilder().setProjectId(_projectId).setInstanceId(_instanceId).build();
            this.adminClient = BigtableTableAdminClient.create(adminSettings);
        } catch (IOException ex) {
            _logger.error(ex.getMessage(), ex);
        }
    }

    public boolean isExistTable(String tableId) {
        return this.adminClient.exists(tableId);
    }

    public boolean createTableIfNotExist(String tableId, List<String> listColumnFamily) {
        if (!isExistTable(tableId)) {
            CreateTableRequest createTableRequest = CreateTableRequest.of(tableId);
            for (String columFamily : listColumnFamily) {
                createTableRequest.addFamily(columFamily);
            }
            Table table = this.adminClient.createTable(createTableRequest);
            return table != null && tableId.equals(table.getId());
        }
        return true;
    }

    public void deleteTable(String tableId) {
        this.adminClient.deleteTable(tableId);
    }
}
