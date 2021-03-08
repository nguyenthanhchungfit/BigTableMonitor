/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigtable.webbackend.controllers;

import com.bigtable.webbackend.bigtable.BigTableModel;
import com.bigtable.webbackend.common.ErrorDefinition;
import com.bigtable.webbackend.entities.MonitorDataResult;
import com.bigtable.webbackend.entities.RegisterAppResult;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author chungnt
 */
@RestController
public class RestChartDataController {

    private static final List<String> _MONITOR_APP = new ArrayList<>();

    @GetMapping("/api/data")
    public MonitorDataResult getDataByAppName(@RequestParam(value = "appName") String appName,
            @RequestParam(value = "from") long fromTime,
            @RequestParam(value = "to") long toTime) {

        if (!(StringUtils.isNotBlank(appName) && fromTime > 0 && toTime > fromTime)) {
            return new MonitorDataResult(ErrorDefinition.ERR_PARAMS_NOT_VALID);
        } else {
            return BigTableModel.INSTANCE.queryData(appName, fromTime, toTime);
        }
//        //random 1000 points data
//        int errorCode = 0;
//        Random r = new Random();
//        int rangeMin = 0;
//        int rangeMax = 100;
//        List<Double> listCpuMetrics = new ArrayList<>();
//        List<Long> listNetWork = new ArrayList<>();
//        List<Long> listHeap = new ArrayList<>();
//        List<Long> listNonHeap = new ArrayList<>();
//        List<Long> listTimeStamps = new ArrayList<>();
//        for (int i = 1; i < 1000; i += 10) {
//            double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble(); // cpu ~ [0;100]
//            long netWorkValue = (long) (randomValue * 1E6); // netWork ~ [0;1E6]
//            long heap = (long) (randomValue * 1E3); // ram ~ [0;1E3]
//            long nonHeap = (long) (1E3 - heap);
//            listCpuMetrics.add(randomValue);
//            listNetWork.add(netWorkValue);
//            listHeap.add(heap);
//            listNonHeap.add(nonHeap);
//            listTimeStamps.add(System.currentTimeMillis() + i * 10000);
//        }
//        MonitorCpuInfo cpuInfo = new MonitorCpuInfo(listCpuMetrics);
//        MonitorNetworkInfo networkInfo = new MonitorNetworkInfo(listNetWork);
//        MonitorRamData ramInfo = new MonitorRamData(listHeap, listNonHeap);
//        MonitorDataResult dataResult = new MonitorDataResult(errorCode, cpuInfo, networkInfo, ramInfo, listTimeStamps);
//        return dataResult;
    }

    @GetMapping("/api/register")
    public RegisterAppResult registerMonitor(@RequestParam(value = "appName") String appName) {
        if (StringUtils.isNotBlank(appName)) {
            if (!_MONITOR_APP.contains(appName)) {
                _MONITOR_APP.add(appName);
            }
            return new RegisterAppResult(0, "Register successfully!!!");
        } else {
            return new RegisterAppResult(ErrorDefinition.ERR_PARAMS_NOT_VALID, "Appname is empty");
        }
    }
}
