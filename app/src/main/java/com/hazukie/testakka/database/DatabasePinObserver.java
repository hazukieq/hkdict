package com.hazukie.testakka.database;

import java.util.Map;

public interface DatabasePinObserver {
    void callOfMsg(String searchValue,Map<String,String> maps);
}
