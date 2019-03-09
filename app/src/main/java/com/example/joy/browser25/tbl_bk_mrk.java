package com.example.joy.browser25;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "bkmrk")
public class tbl_bk_mrk
{
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "site_title")
    private String title;

    @ColumnInfo(name = "site_url")
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
