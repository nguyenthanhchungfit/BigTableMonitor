/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigtable.webbackend.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chungnt
 */
public class JsonUtils {

    private static final ObjectMapper _mapper = new ObjectMapper();

    public static String toJsonStr(Object object) {
        String result = "";
        try {
            result = _mapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(JsonUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
