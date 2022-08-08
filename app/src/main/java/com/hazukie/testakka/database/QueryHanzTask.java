package com.hazukie.testakka.database;

import android.os.AsyncTask;
import android.util.Log;
import com.hazukie.testakka.models.Hkhan_model;
import java.util.ArrayList;
import java.util.List;


public  class QueryHanzTask extends AsyncTask<Void,Void, List<Hkhan_model>> {
    private Hkdatabase hkdatabase;
    private DatabaseObserver observer;
    private List<Hkhan_model> hkhans=new ArrayList<>();
    private String hz="";

    public QueryHanzTask(Hkdatabase hkdatabase, DatabaseObserver observer, String hz){
        this.observer=observer;
        this.hkdatabase=hkdatabase;
        this.hz=hz;
    }

    @Override
    protected List<Hkhan_model> doInBackground(Void... voids) {
        try {
            hkhans.add(hkdatabase.hkhanDao().getHkhan(hz));
        }catch (Exception e){
            e.printStackTrace();
        }
        return hkhans;
    }

    @Override
    protected void onPostExecute(List<Hkhan_model> objects) {
        //Log.i("QeuryTask_objects",objects.size()+","+objects.toString());
        try {
            if(objects.size()>=1) {
                if (objects.get(0) == null && objects.size() == 1) {
                    Log.i("QeuryTask_objects", "长度为空！");
                    objects.add(0, new Hkhan_model(0, "...", "", "", "", "", "", ""));
                    observer.callbackOfMsg(objects);
                } else {
                    //objects.add(0, new Hkhan_model(0, "...", "", "", "", "", "", ""));
                    observer.callbackOfMsg(objects);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}