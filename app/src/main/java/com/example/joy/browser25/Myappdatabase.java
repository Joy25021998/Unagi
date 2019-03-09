package com.example.joy.browser25;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Thistry.class , tbl_bk_mrk.class , newTabTable.class} , version = 1)
public abstract class Myappdatabase extends RoomDatabase
{
    public abstract myDao mydao();
}
