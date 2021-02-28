/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigtable.webbackend.common;

import hapax.TemplateLoader;
import hapax.TemplateResourceLoader;

/**
 *
 * @author chungnt
 */
public class HapaxTemplate {

    public static final TemplateLoader TEMPLATE_LOADER = TemplateResourceLoader.create("templates/");
}
