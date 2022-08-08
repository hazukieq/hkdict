package com.hazukie.testakka.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;

import com.hazukie.testakka.R;
import com.hazukie.testakka.webutils.Keystatics;
import com.hazukie.testakka.webutils.SpvalueStorage;

public class BaseApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }

    public  static  Context getContext(){
        return  context;
    }

}
