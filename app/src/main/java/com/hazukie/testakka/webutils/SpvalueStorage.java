package com.hazukie.testakka.webutils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SpvalueStorage {
    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;
    private static SpvalueStorage instance;

    public SpvalueStorage(Context context){
        sp= PreferenceManager.getDefaultSharedPreferences(context);
        editor=sp.edit();
    }
    public static SpvalueStorage getInstance(Context context) {
        if(instance==null){
           instance=new SpvalueStorage(context);
        }
        return instance;
    }

    public static String  getStringValue(String key,String defaultValue){
        String value=sp.getString(key,defaultValue);
        return value;
    }

    public static void setStringvalue(String key, String value){
        editor.putString(key,value);
        editor.commit();
    }

    public static int getInt(String key,int defaultValue){
        int value=sp.getInt(key,defaultValue);
        return value;
    }

    public static void  setIntValue(String key,int value){
        editor.putInt(key,value);
        editor.commit();

    }
}
