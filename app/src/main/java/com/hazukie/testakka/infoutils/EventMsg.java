package com.hazukie.testakka.infoutils;

public class EventMsg {
    private int type;

    public EventMsg(int type){
        this.type=type;
    }

    public int getType(){
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
