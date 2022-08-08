package com.hazukie.testakka.database;

import android.content.Context;

import com.hazukie.testakka.models.Hkhan_model;

import java.util.List;

public interface DatabaseObserver {
    void callbackOfMsg( List<Hkhan_model> datas);

}
