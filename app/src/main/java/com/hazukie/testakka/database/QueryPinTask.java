package com.hazukie.testakka.database;

import android.os.AsyncTask;
import android.util.Log;

import androidx.sqlite.db.SimpleSQLiteQuery;

import com.hazukie.testakka.models.Hkhan_model;

import java.util.HashMap;
import java.util.Map;

public class QueryPinTask extends AsyncTask<Void,Void, Map<String,String>> {
    private DatabasePinObserver observer;
    private Hkdatabase hkdatabase;
    private String searchValue="";
    private int QUERY_MODE;
    private Map<String,String> hkens=new HashMap<>();

    public QueryPinTask(Hkdatabase hkdatabase,String searchValue,int QUERY_MODE,DatabasePinObserver observer){
        this.hkdatabase=hkdatabase;
        this.searchValue=searchValue;
        this.QUERY_MODE=QUERY_MODE;
        this.observer=observer;
    }

    @Override
    protected Map<String, String> doInBackground(Void... voids) {
        String search_="";
        switch (QUERY_MODE){
            case 0:
                search_="SELECT hz,jetd_gajin_hk FROM simplict WHERE jetd_gajin_hk like '%"+searchValue+"%' limit 20000";
                break;
            case 1:
                search_="SELECT hz,cmn_p FROM simplict WHERE cmn_p like '%"+searchValue+"%' limit 20000";
        }

        Log.i("rawQuery_sentence>>",search_);
        try{
            SimpleSQLiteQuery sqLiteQuery=new SimpleSQLiteQuery(search_);
            if(QUERY_MODE==0) hkens=hkdatabase.hkhanDao().getHkhansByHkQuery(sqLiteQuery);
            else hkens=hkdatabase.hkhanDao().getHkhansByCmnQuery(sqLiteQuery);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(hkens.size()==0) hkens.put("","");
        return hkens;
    }

    @Override
    protected void onPostExecute(Map<String, String> objects) {
        try{
            if(objects.size()>=1) {
                if (objects.get(0) == null && objects.size() == 1) {
                    Log.i("QeuryTask_objects", "长度为空！");
                    objects.put("","");
                    observer.callOfMsg(searchValue,objects);
                } else {
                    //objects.put("","");
                    observer.callOfMsg(searchValue,objects);
                }
            }else{
                objects.put("","");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
