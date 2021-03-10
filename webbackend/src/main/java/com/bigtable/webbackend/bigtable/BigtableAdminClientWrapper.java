/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigtable.webbackend.bigtable;

import com.bigtable.webbackend.entities.ClusterConfig;
import com.bigtable.webbackend.utils.JsonUtils;
import com.google.cloud.bigtable.admin.v2.BigtableInstanceAdminClient;
import com.google.cloud.bigtable.admin.v2.BigtableInstanceAdminSettings;
import com.google.cloud.bigtable.admin.v2.BigtableTableAdminClient;
import com.google.cloud.bigtable.admin.v2.BigtableTableAdminSettings;
import com.google.cloud.bigtable.admin.v2.models.Cluster;
import com.google.cloud.bigtable.admin.v2.models.CreateClusterRequest;
import com.google.cloud.bigtable.admin.v2.models.CreateInstanceRequest;
import com.google.cloud.bigtable.admin.v2.models.CreateTableRequest;
import com.google.cloud.bigtable.admin.v2.models.Instance;
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
    private BigtableTableAdminClient tableAdminClient;
    private BigtableInstanceAdminClient instanceAdminClient;
    
    private static final Logger _logger = LogManager.getLogger(BigtableAdminClientWrapper.class);
    
    public BigtableAdminClientWrapper(String projectId, String instanceId) {
        this._projectId = projectId;
        this._instanceId = instanceId;
        
        BigtableTableAdminSettings tableAdSettings;
        BigtableInstanceAdminSettings instanceAdSettings;
        try {
            tableAdSettings = BigtableTableAdminSettings.newBuilder().setProjectId(_projectId).setInstanceId(_instanceId).build();
            this.tableAdminClient = BigtableTableAdminClient.create(tableAdSettings);
            
            instanceAdSettings = BigtableInstanceAdminSettings.newBuilder().setProjectId(projectId).build();
            this.instanceAdminClient = BigtableInstanceAdminClient.create(instanceAdSettings);
        } catch (IOException ex) {
            _logger.error(ex.getMessage(), ex);
        }
    }
    
    public boolean isExistTable(String tableId) {
        return this.tableAdminClient.exists(tableId);
    }
    
    public boolean createTableIfNotExist(String tableId, List<String> listColumnFamily) {
        if (!isExistTable(tableId)) {
            CreateTableRequest createTableRequest = CreateTableRequest.of(tableId);
            for (String columFamily : listColumnFamily) {
                createTableRequest.addFamily(columFamily);
            }
            Table table = this.tableAdminClient.createTable(createTableRequest);
            return table != null && tableId.equals(table.getId());
        }
        return true;
    }
    
    public void deleteTable(String tableId) {
        this.tableAdminClient.deleteTable(tableId);
    }
    
    public boolean isExistInstance(String instanceId) {
        return this.instanceAdminClient.exists(instanceId);
    }
    
    public boolean createInstanceIfNotExist(String instanceId, ClusterConfig clusterConf) {
        if (!isExistInstance(instanceId)) {
            if (clusterConf.isValidated()) {
                CreateInstanceRequest createInstRequest = CreateInstanceRequest.of(instanceId)
                    .addCluster(clusterConf.getClusterId(), clusterConf.getZone(), clusterConf.getNumNodes(), clusterConf.getStorageType())
                    .setType(clusterConf.getInstanceType())
                    .addLabel(clusterConf.getLabelKey(), clusterConf.getLabelVal());
                Instance instance = this.instanceAdminClient.createInstance(createInstRequest);
                return instance != null;
            } else {
                _logger.error("Cluster config is not validated! " + JsonUtils.toJsonStr(clusterConf));
                return false;
            }
        }
        return true;
    }
    
    public List<Instance> listInstances() {
        return this.instanceAdminClient.listInstances();
    }
    
    public Instance getInstance() {
        return this.instanceAdminClient.getInstance(_instanceId);
    }
    
    public List<Cluster> getListClusters() {
        return this.instanceAdminClient.listClusters(_instanceId);
    }
    
    public Cluster getCluster(String clusterId) {
        return this.instanceAdminClient.getCluster(_instanceId, clusterId);
    }
    
    public void deleteInstance() {
        this.instanceAdminClient.deleteInstance(_instanceId);
    }
    
    public boolean addCluster(ClusterConfig clusterConf) {
        if (clusterConf.isValidated()) {
            Cluster cluster = this.instanceAdminClient.createCluster(CreateClusterRequest.of(_instanceId, clusterConf.getClusterId())
                .setZone(clusterConf.getZone()).setServeNodes(clusterConf.getNumNodes()).setStorageType(clusterConf.getStorageType()));
            return cluster != null;
        }
        return false;
    }
    
    public void deleteCluster(String clusterId) {
        this.instanceAdminClient.deleteCluster(_instanceId, clusterId);
    }
    
}
