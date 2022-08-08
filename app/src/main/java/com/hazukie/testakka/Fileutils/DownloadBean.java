package com.hazukie.testakka.Fileutils;

import java.io.Serializable;

public class DownloadBean implements Serializable {
    public int id;
    public String url;
    public String name;
    public int progress;
    public int download_state;
    public String save_path;
    public long appSize;

    public DownloadBean(String name,int download_state,String url,String save_path,int progress,long appSize){
       // this.id=id;
        this.name=name;
        this.url=url;
        this.progress=progress;
        this.download_state=download_state;
        this.save_path=save_path;
        this.appSize=appSize;
    }
}
