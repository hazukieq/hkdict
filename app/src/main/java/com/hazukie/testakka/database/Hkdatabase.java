package com.hazukie.testakka.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.hazukie.testakka.models.Hkhan_model;

import java.io.File;

@Database(entities = {Hkhan_model.class},version = 1,exportSchema = false)
public abstract class Hkdatabase extends RoomDatabase {
    private static final String DATABASE_NAME="simple_db";

    private static Hkdatabase instance;

    public static synchronized Hkdatabase getInstance(Context context){
        File database_path=new File(context.getDir("datas",Context.MODE_PRIVATE),"simple_db.db");
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),Hkdatabase.class,DATABASE_NAME)
                    .createFromFile(database_path)
                    .build();
        }
        return instance;
    }
    public abstract HkhanDao hkhanDao();
}
