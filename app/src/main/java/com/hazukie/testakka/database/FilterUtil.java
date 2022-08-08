package com.hazukie.testakka.database;

import android.util.Log;

import com.hazukie.testakka.models.Hkhan_model;
import com.hazukie.testakka.models.Pins;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterUtil {

   /* public static List<String> filterHKPins(String target, List<?> datas){
        List<String> hans=new ArrayList<>();
        for(int q=0;q<datas.size();q++){
            Hkhan_model hk=(Hkhan_model) datas.get(q);
            String[] jetd_pins=hk.jetd_gajin_hk.split(",");
            String pattern="^"+target+"(\\d)?$";
            Pattern p=Pattern.compile(pattern);

            for(int g=0;g<jetd_pins.length;g++){
                String jetd=jetd_pins[g];
                Matcher matcher=p.matcher(jetd);
                if(matcher.find()){
                    Log.i("find",hk.hz);
                    hans.add(hk.hz);
                    break;
                }else{
                    continue;
                }
            }

        }
        return hans;
    }*/

    public static List<Pins> filterCmnPins(String target, Map<String,String> datas){
        String pattern="";
        char c=target.charAt(target.length()-1);
        if(Character.isDigit(c)){
            pattern="^"+target+"$";
            Log.i("filrerCmnpins","pattern>>"+pattern);
        }else {pattern="^"+target+"(\\d)?$";}

        Map<String,List<String>> hans=new HashMap<>();
        for(String hanzKey:datas.keySet()){
            String[] cmnps= Objects.requireNonNull(datas.get(hanzKey)).split(",");
            Pattern p=Pattern.compile(pattern);
            for(int i=0;i<cmnps.length;i++){
                String cmnp=cmnps[i];
                Matcher matcher=p.matcher(cmnp);
                if(matcher.find()){
                    List<String> persist=hans.get(cmnp)==null?new ArrayList<>():hans.get(cmnp);
                    persist.add(hanzKey);
                    hans.put(cmnp, persist);
                }
            }
        }

        Log.i("filterPins>>",hans.keySet().toString()+":"+hans.values().toString());

        List<Pins> returnPins=new ArrayList<>();
        for(String obj:hans.keySet()){
            List<String> azs=hans.get(obj);
            returnPins.add(new Pins(azs,azs,obj));
        }
        return returnPins;
    }
}
