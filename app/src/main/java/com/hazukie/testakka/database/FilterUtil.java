package com.hazukie.testakka.database;


import com.hazukie.testakka.models.Pins;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterUtil {

    public static List<Pins> filterCmnPins(String target, Map<String,String> datas){
        String pattern="";
        char c=target.charAt(target.length()-1);
        if(Character.isDigit(c)) pattern="^"+target+"$";
        else pattern="^"+target+"(\\d)?$";

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

        List<Pins> returnPins=new ArrayList<>();
        for(String obj:hans.keySet()){
            List<String> azs=hans.get(obj);
            returnPins.add(new Pins(azs,azs,obj));
        }
        return returnPins;
    }
}
