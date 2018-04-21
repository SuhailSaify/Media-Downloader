package com.example.suhail.videodownloader.Model;

/**
 * Created by Suhail on 4/4/2018.
 */

public class Urls {
    String url;
    int id;
    boolean isdownloaded;
    String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {

        this.path = path;
    }

    public Urls(String url, int id, boolean isdownloaded, String path) {

        this.url = url;
        this.id = id;
        this.isdownloaded = isdownloaded;
        this.path = path;
    }

    public void setIsdownloaded(boolean isdownloaded) {
        this.isdownloaded = isdownloaded;
    }

    public boolean isIsdownloaded() {

        return isdownloaded;
    }

    public String getUrl() {
        return url;
    }



    public void setId(int id) {
        this.id = id;
    }

    public int getId() {

        return id;
    }


    /**
     * Created by Suhail on 4/7/2018.
     */
}