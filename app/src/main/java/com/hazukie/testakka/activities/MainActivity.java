package com.hazukie.testakka.activities;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.hazukie.testakka.R;
import com.hazukie.testakka.adapters.PagerAdapter;
import com.hazukie.testakka.base.BaseActivity;
import com.hazukie.testakka.base.QViewpager2;
import com.hazukie.testakka.database.Hkdatabase;
import com.hazukie.testakka.fragments.searchFrag;
import com.hazukie.testakka.fragments.translationFrag;
import com.hazukie.testakka.fragments.wordFrag;
import com.hazukie.testakka.infoutils.EventMsg;
import com.hazukie.testakka.models.Hkhan_model;
import com.hazukie.testakka.webutils.Keystatics;
import com.hazukie.testakka.webutils.SpvalueStorage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private TabLayout tabs;
    private QViewpager2 viewPager2;
    private PagerAdapter adapter;
    private DrawerLayout drawerLayout;
    private Button app_settings,app_hkintroduce;
    private boolean isScroll=true;
    private String setting_url="file:///android_asset/setting.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout= findViewById(R.id.main_app_drawer);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        viewPager2= findViewById(R.id.main_app_pagers);
        tabs= findViewById(R.id.main_app_tabs);
        app_settings= findViewById(R.id.app_setting);
        app_hkintroduce= findViewById(R.id.app_hkintroduce);

        app_hkintroduce
                .setOnClickListener(v -> DialectDetailActivity
                        .startActivityWithLoadUrl(MainActivity.this  ,
                                DialectDetailActivity.class,
                                "https://blog.hazukieq.top/html/unihk_introduce.html","通用客家话拼音方案",
                                "file:///android_asset/dialect.html"));


        app_settings.setOnClickListener(v -> SettingActivity
                .startActivityWithLoadUrl(MainActivity.this,
                        SettingActivity.class,setting_url,
                        "应用设置",""));

        SpvalueStorage.getInstance(this);
        int isOffline=SpvalueStorage.getInt("searchWeb",0);
        initPagers(isOffline);
        setViewPager2Scroll();
    }

    /*@Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventMsg msg){
        Log.i( "onReceiveMsg: ",""+msg);
        MainActivity.this.finish();
        Intent intent=new Intent(this,MainActivity.class);
        overridePendingTransition(0,0);
        startActivity(intent);
    }*/


    private void initPagers(int isOffline){
        FragmentManager fm=getSupportFragmentManager();
        adapter=new PagerAdapter(fm) {
            @Override
            public Fragment createFragment(int position) {
                    switch (position) {
                        case 0:
                            String search_url="file:///android_asset/lindex.html";
                            if(isOffline==1) search_url="file:///android_asset/offline/lindex_offline.html";
                            return new searchFrag(search_url);
                        case 1:
                            return new wordFrag("file:///android_asset/lwords.html");
                        case 2:
                            return new translationFrag("file:///android_asset/lhelp.html");//"http://43.142.122.229");
                        default:
                            return null;
                    }
            }

/*    private void initPagers(int isNightOrNot){
        FragmentManager fm=getSupportFragmentManager();
        adapter=new PagerAdapter(fm) {
            @Override
            public Fragment createFragment(int position) {
                if(isNightOrNot==0) {
                    switch (position) {
                        case 0:
                            return new searchFrag("file:///android_asset/lindex_offline.html");
                        case 1:
                            return new wordFrag("file:///android_asset/lwords.html");
                        case 2:
                            return new translationFrag("file:///android_asset/lhelp.html");//"http://43.142.122.229");
                        default:
                            return null;
                    }
                }else{
                    switch (position) {
                        case 0:
                            //searchFrag searc=new searchFrag("file:///android_asset/nights/lindex.html");
                            return new searchFrag("file:///android_asset/nights/lindex.html");
                        case 1:
                            return new wordFrag("file:///android_asset/nights/lwords.html");
                        case 2:
                            return new translationFrag("file:///android_asset/nights/lhelp.html");//"http://43.142.122.229");
                        default:
                            return null;
                    }
                }
            }*/


            @Override
            public int getCount() {
                return 3;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                switch (position){
                    case 0:
                        return "首页";
                    case 1:
                        return "词汇";
                    case 2:
                        return "翻译";
                    default:
                        return "";
                }
            }
        };

        viewPager2.setAdapter(adapter);
        viewPager2.setOffscreenPageLimit(3);
        tabs.setupWithViewPager(viewPager2);
    }


    public void setViewPager2Scroll(){
        SpvalueStorage.getInstance(MainActivity.this);
        int s= SpvalueStorage.getInt(Keystatics.keys[1],1 );
        if(s==0){
            viewPager2.setCanScroll(false);
        }
        else if(s==1) viewPager2.setCanScroll(true);
    }

    public void showDrawer(){
        drawerLayout.openDrawer(Gravity.LEFT);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN){
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setViewPager2Scroll();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}