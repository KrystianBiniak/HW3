package com.example.seriesandmovieslist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Series{

    private String name;
    private String platform;
    private String year;
    private String imgURL;
    private String dbID;
    private int ID;


    public Series() {
    }

    public Series(String name, String platform, String year, String dbID, int ID, String imgURL) {
        this.name = name;
        this.platform = platform;
        this.year = year;
        this.dbID = dbID;
        this.imgURL = imgURL;
        this.ID = ID;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) { this.year = year; }

    public String getDbID() { return dbID; }

    public void setDbID(String dbID) { this.dbID = dbID; }

    public int getID() { return ID;}

    public void setID(int ID) { this.ID = ID; }



    public void openAddItemActivity(){

    }
}
