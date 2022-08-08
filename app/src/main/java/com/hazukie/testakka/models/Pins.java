/**
 * Copyright 2022 json.cn
 */
package com.hazukie.testakka.models;
import java.util.List;

/**
 * Auto-generated: 2022-08-07 3:3:32
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class Pins {

    private List<String> hanzs;
    private List<String> links;
    private String pin;

    public Pins(List<String> hanzs,List<String> links,String pin){
        this.hanzs=hanzs;
        this.links=links;
        this.pin=pin;
    }


    public void setHanzs(List<String> hanzs) {
        this.hanzs = hanzs;
    }
    public List<String> getHanzs() {
        return hanzs;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }
    public List<String> getLinks() {
        return links;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
    public String getPin() {
        return pin;
    }

}