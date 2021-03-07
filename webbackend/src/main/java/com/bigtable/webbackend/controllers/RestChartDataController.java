/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigtable.webbackend.controllers;

import com.bigtable.webbackend.entities.MonitorCpuInfo;
import com.bigtable.webbackend.entities.MonitorDataResult;
import com.bigtable.webbackend.entities.MonitorNetworkInfo;
import com.bigtable.webbackend.entities.MonitorRamData;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author chungnt
 */
@RestController
public class RestChartDataController {

    @GetMapping("/api/data")
    public MonitorDataResult getDataByAppName(@RequestParam(value = "appName") String appName,
        @RequestParam(value = "from") long fromTime,
        @RequestParam(value = "to") long toTime) {
        System.out.println("appName: " + appName);
        System.out.println("fromTime: " + fromTime);
        System.out.println("toTime: " + toTime);

        int errorCode = 0;
        MonitorCpuInfo cpuInfo = new MonitorCpuInfo(Arrays.asList(0.5, 0.2, 0.4));
        MonitorNetworkInfo networkInfo = new MonitorNetworkInfo(Arrays.asList(10000l, 10002l, 11000l));
        MonitorRamData ramInfo = new MonitorRamData(Arrays.asList(10l, 10l, 10l), Arrays.asList(5l, 6l, 7l));
        List<Long> timestamps = Arrays.asList(1615124074642l, 1615124079642l, 1615124084642l);
        MonitorDataResult dataResult = new MonitorDataResult(errorCode, cpuInfo, networkInfo, ramInfo, timestamps);
        return dataResult;
    }

    @PostMapping("/api/register")
    public String registerMonitor() {
        return "";
    }
}
