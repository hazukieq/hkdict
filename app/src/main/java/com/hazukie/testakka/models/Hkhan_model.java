package com.hazukie.testakka.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "simplict")
public class Hkhan_model {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id",typeAffinity = ColumnInfo.INTEGER)
    public int id;

    @ColumnInfo(name = "hz",typeAffinity = ColumnInfo.TEXT)
    public String hz;

    @ColumnInfo(name = "bh",typeAffinity = ColumnInfo.TEXT)
    public String bh;

    @ColumnInfo(name = "ps",typeAffinity = ColumnInfo.TEXT)
    public String ps;

    @ColumnInfo(name = "cmn_p",typeAffinity = ColumnInfo.TEXT)
    public String cmn_p;

    @ColumnInfo(name = "va",typeAffinity = ColumnInfo.TEXT)
    public String va;

    @ColumnInfo(name = "hd",typeAffinity = ColumnInfo.TEXT)
    public String hd;

    @ColumnInfo(name = "jetd_gajin_hk",typeAffinity = ColumnInfo.TEXT)
    public String jetd_gajin_hk;

    public Hkhan_model(int id,String hz,String bh,String ps,String va,String cmn_p,String hd,String jetd_gajin_hk){
        this.id=id;
        this.hz=hz;
        this.bh=bh;
        this.ps=ps;
        this.cmn_p=cmn_p;
        this.hd=hd;
        this.va=va;
        this.jetd_gajin_hk=jetd_gajin_hk;
    }

}
