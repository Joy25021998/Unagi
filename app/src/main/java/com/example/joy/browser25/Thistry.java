package com.example.joy.browser25;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "histries")
public class Thistry
{
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "hurls")
    private String hurl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHurl() {
        return hurl;
    }

    public void setHurl(String hurl) {
        this.hurl = hurl;
    }
}
