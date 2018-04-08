package com.example.suhail.videodownloader.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.suhail.videodownloader.Model.DownloadedVideos;

import com.example.suhail.videodownloader.Model.Urls;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Suhail on 4/7/2018.
 */

public class DownloadedVidShared {

    SharedPreferences mPrefs;
    Gson gson = new Gson();
    Type listOfObjects;
    Type listOfObjects2;
    SharedPreferences sharedPreferences;
    Activity activity;
    //Editor for shared preferences..
    SharedPreferences.Editor editor;
    ArrayList<DownloadedVideos> downloadedVideos = new ArrayList<>();
    Context context;
    ArrayList<Urls> urls = new ArrayList<>();



    public DownloadedVidShared(Context context, Activity activity) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();


        mPrefs = activity.getPreferences(context.MODE_PRIVATE);

        listOfObjects = new TypeToken<ArrayList<DownloadedVideos>>() {
        }.getType();
        String json = mPrefs.getString("MyList", "");


        listOfObjects2 = new TypeToken<ArrayList<Urls>>() {
        }.getType();

    }

    public void saveURL(ArrayList<Urls> urls) {

        this.urls = urls;

        String strObject = gson.toJson(urls, listOfObjects2); // Here list is your List<CUSTOM_CLASS> object
        SharedPreferences.Editor prefsEditor = mPrefs.edit();

        prefsEditor.putString("URLS", strObject);

        prefsEditor.apply();
    }

    public void savevid(ArrayList<DownloadedVideos> downloadedVidShareds) {

        this.downloadedVideos = downloadedVidShareds;

        String strObject = gson.toJson(downloadedVidShareds, listOfObjects); // Here list is your List<CUSTOM_CLASS> object
        SharedPreferences.Editor prefsEditor = mPrefs.edit();

        prefsEditor.putString("MyList", strObject);

        prefsEditor.apply();
    }

    public ArrayList<Urls> geturl() {

        String json = mPrefs.getString("URLS", "");
        return gson.fromJson(json, listOfObjects2);
    }

    public ArrayList<DownloadedVideos> getvid() {

        String json = mPrefs.getString("MyList", "");
        return gson.fromJson(json, listOfObjects);
    }

}
