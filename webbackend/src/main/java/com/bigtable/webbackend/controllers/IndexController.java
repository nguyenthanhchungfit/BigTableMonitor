/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigtable.webbackend.controllers;

import com.bigtable.webbackend.common.HapaxTemplate;
import hapax.Template;
import hapax.TemplateDataDictionary;
import hapax.TemplateDictionary;
import hapax.TemplateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author chungnt
 */
@RestController
public class IndexController {

    @RequestMapping("/")
    public String index() {
        String content = "Greetings from Spring Boot!";
        TemplateDataDictionary dict = TemplateDictionary.create();
        try {
            Template tmpl = HapaxTemplate.TEMPLATE_LOADER.getTemplate("hello");
            content = tmpl.renderToString(dict);
        } catch (TemplateException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return content;
    }
}
