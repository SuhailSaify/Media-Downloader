package com.example.suhail.videodownloader.Model;

import com.example.suhail.videodownloader.Fragments.ShowDownloaded;

/**
 * Created by Suhail on 4/7/2018.
 */

public class DownloadedVideos {

String path;
String name;
String url;

    public String getUrl() {
        return url;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public DownloadedVideos(String name, String path,String url) {

        this.path = path;
        this.name = name;
    }
}
