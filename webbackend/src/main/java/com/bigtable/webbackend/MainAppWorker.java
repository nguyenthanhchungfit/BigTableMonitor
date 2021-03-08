/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigtable.webbackend;

import com.bigtable.webbackend.bigtable.BigTableModel;
import com.bigtable.webbackend.entities.RegisterAppResult;
import com.bigtable.webbackend.models.CollectorsModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
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
            String urlHost = iniReader.get("collector", "urlHost");
            int intervalTime = NumberUtils.toInt(intervalStr, 5000);
            boolean registerSuccess = _registerAppName(urlHost + "/api/register?appName=" + appName);
            if (registerSuccess && BigTableModel.INSTANCE.initTable()) {
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

    private static boolean _registerAppName(String urlHost) {
        boolean isSuccess = false;
        URL url;
        try {
            url = new URL(urlHost);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            ObjectMapper _objectMapper = new ObjectMapper();
            RegisterAppResult registerResult = _objectMapper.readValue(content.toString(), RegisterAppResult.class);
            return registerResult.getError() == 0; // success

        } catch (MalformedURLException ex) {
            _logger.error(ex.getMessage(), ex);
        } catch (ProtocolException ex) {
            _logger.error(ex.getMessage(), ex);
        } catch (IOException ex) {
            _logger.error(ex.getMessage(), ex);
        }
        return isSuccess;
    }
}
