/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigtable.webbackend.controllers;

import com.bigtable.webbackend.bigtable.BigTableModel;
import com.bigtable.webbackend.common.ErrorDefinition;
import com.bigtable.webbackend.entities.MonitorCpuInfo;
import com.bigtable.webbackend.entities.MonitorDataResult;
import com.bigtable.webbackend.entities.MonitorNetworkInfo;
import com.bigtable.webbackend.entities.MonitorRamData;
import com.bigtable.webbackend.entities.RegisterAppResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

//        int errorCode = 0;
//        MonitorCpuInfo cpuInfo = new MonitorCpuInfo(Arrays.asList(0.5, 0.2, 0.4));
//        MonitorNetworkInfo networkInfo = new MonitorNetworkInfo(Arrays.asList(10000l, 10002l, 11000l));
//        MonitorRamData ramInfo = new MonitorRamData(Arrays.asList(10l, 10l, 10l), Arrays.asList(5l, 6l, 7l));
//        List<Long> timestamps = Arrays.asList(1615124074642l, 1615124079642l, 1615124084642l);
//        MonitorDataResult dataResult = new MonitorDataResult(errorCode, cpuInfo, networkInfo, ramInfo, timestamps);
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
