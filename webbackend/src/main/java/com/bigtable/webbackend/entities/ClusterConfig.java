/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigtable.webbackend.entities;

import com.google.cloud.bigtable.admin.v2.models.Instance;
import com.google.cloud.bigtable.admin.v2.models.StorageType;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author chungnt
 */
public class ClusterConfig {

    private String clusterId;
    private String zone;
    private int numNodes;
    private StorageType storageType;
    private Instance.Type instanceType;
    private String labelKey;
    private String labelVal;

    public ClusterConfig(String clusterId, String zone, int numNodes, StorageType storageType, Instance.Type instanceType, String labelKey, String labelVal) {
        this.clusterId = clusterId;
        this.zone = zone;
        this.numNodes = numNodes;
        this.storageType = storageType;
        this.instanceType = instanceType;
        this.labelKey = labelKey;
        this.labelVal = labelVal;
        if (numNodes < 0) {
            numNodes = 1;
        }
    }

    public boolean isValidated() {
        return StringUtils.isNoneBlank(clusterId) && StringUtils.isNotBlank(zone) && numNodes > 0;
    }

    public int getNumNodes() {
        return numNodes;
    }

    public void setNumNodes(int numNodes) {
        this.numNodes = numNodes;
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public StorageType getStorageType() {
        return storageType;
    }

    public void setStorageType(StorageType storageType) {
        this.storageType = storageType;
    }

    public Instance.Type getInstanceType() {
        return instanceType;
    }

    public void setInstanceType(Instance.Type instanceType) {
        this.instanceType = instanceType;
    }

    public String getLabelKey() {
        return labelKey;
    }

    public void setLabelKey(String labelKey) {
        this.labelKey = labelKey;
    }

    public String getLabelVal() {
        return labelVal;
    }

    public void setLabelVal(String labelVal) {
        this.labelVal = labelVal;
    }

}
