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
public class NetworkInfo {

    private long networkUsage;

    public NetworkInfo(long networkUsage) {
        this.networkUsage = networkUsage;
    }

    public long getNetworkUsage() {
        return networkUsage;
    }

    public void setNetworkUsage(long networkUsage) {
        this.networkUsage = networkUsage;
    }

}
