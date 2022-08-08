package com.hazukie.testakka.models;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hkcategories {
        private List<String> codes;
        private List<String> names;
    public Hkcategories(List<String> codes,List<String> names){
        this.codes=codes;
        this.names=names;
    }
        public void setCodes(List<String> codes) {
            this.codes = codes;
        }
        public List<String> getCodes() {
            return codes;
        }

        public void setNames(List<String> names) {
            this.names = names;
        }
        public List<String> getNames() {
            return names;
        }

    @Override
    public String toString() {
        return "Hkcategories{" +
                "codes=" + codes +
                ", names=" + names +
                '}';
    }

    public String toFormatedStrings(){
        StringBuilder builder=new StringBuilder();
        for(int y=0;y<names.size();y++){
            builder.append(String.format("%s,%s-",codes.get(y),names.get(y)));
        }
        return builder.toString();
    }

    public static Hkcategories transfer2Hk(String str){
        String[] strings=str.split("-");
        List<String> namq=new ArrayList<>();
        List<String> codeq=new ArrayList<>();

        for(int i=0;i< strings.length;i++){
            String srt=strings[i];
            String[] rt=srt.split(",");
            namq.add(rt[1]);
            codeq.add(rt[0]);
        }
        return new Hkcategories(codeq,namq);
    }
}
