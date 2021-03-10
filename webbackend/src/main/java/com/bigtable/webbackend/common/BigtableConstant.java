/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigtable.webbackend.common;

/**
 *
 * @author chungnt
 */
public class BigtableConstant {

    public enum Zone {
        US_CENTRAL1_A("us-central1-a"),
        US_CENTRAL1_B("us-central1-b"),
        US_CENTRAL1_C("us-central1-c"),
        US_CENTRAL1_F("us-central1-f"),
        ASIA_SOUTHEAST1_SING_A("asia-southeast1-a"),
        ASIA_SOUTHEAST1_SING_B("asia-southeast1-b"),
        ASIA_SOUTHEAST1_SING_C("asia-southeast1-c");

        private String name;

        private Zone(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
