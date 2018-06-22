package com.aman.amanapp;

import android.graphics.drawable.Drawable;

/**
 * Created by aman on 6/11/2017.
 */

public class AppItem {
    String name;
    Drawable icon;
    String pacakgeName;

    public String getAccessTimes() {
        return accessTimes;
    }

    public AppItem(String name, Drawable icon, String pacakgeName, String accessTimes) {
        this.name = name;
        this.icon = icon;
        this.pacakgeName = pacakgeName;
        this.accessTimes = accessTimes;
    }

    public AppItem(String name, String pacakgeName, String accessTimes) {
        this.name = name;
        this.pacakgeName = pacakgeName;
        this.accessTimes = accessTimes;
    }

    public void setAccessTimes(String accessTimes) {
        this.accessTimes = accessTimes;
    }

    String accessTimes;

    public String getPacakgeName() {
        return pacakgeName;
    }

    public void setPacakgeName(String pacakgeName) {
        this.pacakgeName = pacakgeName;
    }

    public AppItem(String name, Drawable icon, String pacakgeName) {
        this.name = name;
        this.icon = icon;
        this.pacakgeName = pacakgeName;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
