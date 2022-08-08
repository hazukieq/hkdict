package com.hazukie.testakka.webutils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.hazukie.testakka.Fileutils.FileUtil;
import com.hazukie.testakka.models.Hkcategories;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Gexhttp {

    public static void sendMsg(Context context, String url,int day) {
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.i("Gexhttp_callback:","request failed!");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String jsons=response.body().string();

                if(jsons.length()>0&&!jsons.contains("<")){
                    Gson gson=new Gson();
                    Hkcategories hkcategories=gson.fromJson(jsons,Hkcategories.class);
                    Log.i( "Gexhttp_response---> ",hkcategories.toFormatedStrings());
                    String cd=hkcategories.toFormatedStrings();
                    boolean isWrite=new FileUtil(context).writeF("hkcategories",cd);
                    if(isWrite){
                        SpvalueStorage.setIntValue("hkcategories_date",day);
                    }
                    Log.i("Gexhttp_isWrite-->",String.valueOf(isWrite));
                }
            }
        });

    }
}
