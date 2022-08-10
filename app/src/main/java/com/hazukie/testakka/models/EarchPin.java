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
public class EarchPin {

    public String input_type;
    public String origin;
    public List<Pins> pins;

    public EarchPin(String input_type, String origin, List<Pins> pins){
        this.input_type=input_type;
        this.origin=origin;
        this.pins=pins;
    }
    public void setInput_type(String input_type) {
        this.input_type = input_type;
    }
    public String getInput_type() {
        return input_type;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
    public String getOrigin() {
        return origin;
    }

    public void setPins(List<Pins> pins) {
        this.pins = pins;
    }
    public List<Pins> getPins() {
        return pins;
    }

}
