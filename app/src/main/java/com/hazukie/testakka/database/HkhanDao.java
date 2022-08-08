package com.hazukie.testakka.database;

import androidx.room.Dao;
import androidx.room.MapInfo;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.hazukie.testakka.models.Hkhan_model;

import java.util.List;
import java.util.Map;

@Dao
public interface HkhanDao {
    @Query("SELECT * FROM simplict")
    List<Hkhan_model> getHkhanList();

    @Query("SELECT * FROM simplict WHERE hz=:hz")
    Hkhan_model getHkhan(String hz);

    @RawQuery
    @MapInfo(keyColumn = "hz",valueColumn = "jetd_gajin_hk")
    Map<String,String> getHkhansByHkQuery(SupportSQLiteQuery query);

    @RawQuery
    @MapInfo(keyColumn = "hz",valueColumn = "cmn_p")
    Map<String,String> getHkhansByCmnQuery(SupportSQLiteQuery query);
}
