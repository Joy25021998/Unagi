package com.example.joy.browser25;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface myDao
{
    @Insert
    public void insert(Thistry thistry);

    @Insert
    public void ins_mrk(tbl_bk_mrk tblBkMrk);

    @Query("select * from histries")
    public List<Thistry> display();

    @Query("select * from bkmrk")
    public List<tbl_bk_mrk> bmdis();

    @Delete
    public void del_his(Thistry thistry);

    @Delete
    public void del_bk(tbl_bk_mrk aa);

    @Update
    public void update_bk(tbl_bk_mrk df);

    @Insert
    public void insert_tab(newTabTable newTabTable);

    @Query("select * from tabs")
    public List<newTabTable> tables();

    @Delete
    public void deleteTab(newTabTable newTabTable);
}
