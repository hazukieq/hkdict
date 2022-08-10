package com.hazukie.testakka.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.webkit.JavascriptInterface;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hazukie.testakka.fileutils.FileUtil;
import com.hazukie.testakka.activities.HanzdetailActivity;
import com.hazukie.testakka.activities.MainActivity;
import com.hazukie.testakka.activities.SettingActivity;
import com.hazukie.testakka.base.CnWebView;
import com.hazukie.testakka.base.UniversalWebView;
import com.hazukie.testakka.database.DatabaseObserver;
import com.hazukie.testakka.database.DatabasePinObserver;
import com.hazukie.testakka.database.FilterUtil;
import com.hazukie.testakka.database.Hkdatabase;
import com.hazukie.testakka.database.QueryHanzTask;
import com.hazukie.testakka.database.QueryPinTask;
import com.hazukie.testakka.models.EarchHz;
import com.hazukie.testakka.models.EarchPin;
import com.hazukie.testakka.models.Hkcategories;
import com.hazukie.testakka.models.Hkhan_model;
import com.hazukie.testakka.models.Pins;
import com.hazukie.testakka.webutils.Keystatics;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link searchFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class searchFrag extends UniversalWebView {
    private static String host_url="http://dict.hazukieq.top/searchapis/returnhz";
    private static String local_url="hkapp://search_/";
    private static String local_reload="hkapp://reload_/";
    private static String offline_url="hkoffline://search_/?s=";
    private List<Hkhan_model> alls=new ArrayList<>();


        public searchFrag(String openurl){
            open_url=openurl;
        }


        @Override
        protected void configWebView(CnWebView webView) {
            super.configWebView(webView);
            webView.addJavascriptInterface(new BtnClick(getActivity()), "app");
        }


        public class BtnClick{
            MainActivity activity=(MainActivity)getActivity();

            public BtnClick(Context context){

            }

            @JavascriptInterface
            public void openDrawer() {
                activity.showDrawer();
            }

            @JavascriptInterface
            public void info(String str){
                Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
            }

            @JavascriptInterface
            public void copyAll(String str){
                ClipboardManager cm=(ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipDta=ClipData.newPlainText("Pinhanzs",str);
                cm.setPrimaryClip(mClipDta);
                Toast.makeText(getActivity(), "复制成功！", Toast.LENGTH_SHORT).show();
            }


            @JavascriptInterface
            public String requestHkcategories(){
                String jsons="";
                Gson gson=new Gson();
                try{
                    jsons=new FileUtil(getActivity()).readFs("hkcategories");
                    if(jsons.length()>2){
                        Hkcategories hk=Hkcategories.transfer2Hk(jsons);
                        jsons=gson.toJson(hk);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
                return jsons;
            }


            @JavascriptInterface
            public void OfAjax(String url){
                Gson gson=new Gson();
                Hkdatabase hkdatabase= Hkdatabase.getInstance(getActivity());

                if(url.startsWith("hkoffline/search_hz_")){
                    String[] urlq=url.replace("hkoffline/search_hz_","").split(",");
                    String anz=urlq[0];
                    if(urlq[0].equals("kull")|urlq[0].contains(" ")) anz="一";
                    new QueryHanzTask(hkdatabase, new DatabaseObserver() {
                        @Override
                        public void callbackOfMsg(List<Hkhan_model> datas) {
                            if(datas.size()==0){
                                datas.add(0,new Hkhan_model(0,"...","","","","","",""));
                            }

                            Hkhan_model model=(Hkhan_model) datas.get(0);
                            EarchHz earchHz=new EarchHz(model.hz, model.bh,model.cmn_p,model.ps,"0");
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mWebView.loadUrl("javascript:controlResultLayout("+gson.toJson(earchHz)+")");
                                }
                            });
                        }
                    }, anz).execute();

                }

                else{
                    String[] urlq=url.replace("hkoffline/search_pin_","").split(",");
                    String anz=urlq[0];
                    if(urlq[0].equals("kull")|urlq[0].contains(" ")) anz="li";
                    int QUERY_MODE=0;
                    if(urlq[1].equals("cmnp"))QUERY_MODE=1;
                    //Log.i("onRaw_QUERY_MODE>>",""+QUERY_MODE);
                    new QueryPinTask(hkdatabase, anz,QUERY_MODE, new DatabasePinObserver() {
                        @Override
                        public void callOfMsg(String searchValue,Map<String, String> maps) {

                            List<Pins> cmnPins=FilterUtil.filterCmnPins(searchValue,maps);
                            Log.i("onRaw_cmnPins>>",""+cmnPins.size());

                            EarchPin earchPin=new EarchPin("1",searchValue,cmnPins);
                            String earchPin_jsons=gson.toJson(earchPin);
                            Log.i("jsons-->",earchPin_jsons);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mWebView.loadUrl("javascript:controlResultLayout("+earchPin_jsons+")");
                                }
                            });
                        }
                    }).execute();
                }

            }

            @JavascriptInterface
            public void openSetting(){
                SettingActivity.startActivityWithLoadUrl(getActivity(),SettingActivity.class,"file:///android_asset/setting.html","应用设置","");
            }
        }

    @Override
    protected WebViewClient getWebViewClient() {
        return new UniwebClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                try {
                    url = URLDecoder.decode(url, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                //SpvalueStorage.getInstance(getActivity());
                //这里对APP本地url进行拦截，并通过修改原始数据并重新生成url链接来向服务器发送请求；规定协议为hkapp://;
                 if(url.startsWith(local_url)){
                    String sid=url.replace(local_url,host_url);//+"lindex.html");
                    String Localurl="file:///android_asset/lindex_results.html";

                   // if(SpvalueStorage.getInt("currentTheme",0)==1) Localurl="file:///android_asset/nights/lindex_results.html";
                    HanzdetailActivity.startActivityWithLoadUrl(getActivity(),HanzdetailActivity.class,Localurl,"详细信息",sid);

                    return true;
                }
                 else if(url.startsWith(offline_url)){
                     String murl=url.replace(offline_url,"");
                     String Localmurl="file:///android_asset/offline/lindex_results_offline.html";
                     HanzdetailActivity.startActivityWithLoadUrl(getActivity(),HanzdetailActivity.class,Localmurl,"详细信息",murl);

                     return true;
                 }

                 else if(url.startsWith(local_reload)){

                     String si=url.replace(local_reload,"");

                     getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mWebView.loadUrl("javascript:AsyncJSON('"+si+"')");
                        }
                    });
                    return true;
                }
                return false;
            }
        };
}



public void CheckIsOffline(){
    getActivity().runOnUiThread(() -> {
        int isOffline=sp.getInt(Keystatics.keys[0], 0);
        int currentWeb=sp.getInt("searchWeb",0);
        String search_url="file:///android_asset/lindex.html";
        if(isOffline==1){
            search_url="file:///android_asset/offline/lindex_offline.html";
        }

        if(isOffline!=currentWeb){
            editor.putInt("searchWeb",isOffline);
            editor.commit();
            mWebView.loadUrl(search_url);
        }
    });
}

    @Override
    public void onResume() {
        super.onResume();
        CheckIsOffline();
    }
}
