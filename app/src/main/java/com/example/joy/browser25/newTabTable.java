package com.example.joy.browser25;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "tabs")
public class newTabTable
{

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "tabTitle")
    private String title;

    @ColumnInfo(name = "tabUrl")
    private String url;

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

}
