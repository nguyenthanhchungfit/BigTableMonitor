/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigtable.webbackend;

import com.bigtable.webbackend.bigtable.BigTableModel;
import com.bigtable.webbackend.models.CollectorsModel;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.ini4j.Wini;

/**
 *
 * @author chungnt
 */
public class MainAppWorker {
    
    private static final org.apache.logging.log4j.Logger _logger = LogManager.getLogger(MainAppWorker.class);
    
    public static void main(String[] args) {
        
        try {
            Wini iniReader = new Wini(new File("conf/conf.ini"));
            String appName = iniReader.get("collector", "appName");
            String intervalStr = iniReader.get("collector", "intervalStr");
            int intervalTime = NumberUtils.toInt(intervalStr, 5000);
            if (BigTableModel.INSTANCE.initTable()) {
                while (true) {
                    
                    CollectorsModel.collectAndSubmitData(appName);
                    
                    Thread.sleep(intervalTime);
                }
            } else {
                _logger.info("Worker stopped!");
            }

            // start worker
            // submit data app to webapp
            // read config worker: name, interval collect
            // loop collect data and submit data to bigtable
        } catch (IOException ex) {
            _logger.error(ex.getMessage(), ex);
        } catch (InterruptedException ex) {
            _logger.error(ex.getMessage(), ex);
        }
    }
}
