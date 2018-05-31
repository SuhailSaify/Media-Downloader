package com.example.filesmanager.videodownloader.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Suhail on 4/8/2018.
 */

public class SavelastUrl {

    String SAVE = "ABCD";
    SharedPreferences mPrefs;
    Context context;
    SharedPreferences sharedPreferences;
    Activity activity;
    //Editor for shared preferences..
    SharedPreferences.Editor editor;

    public SavelastUrl(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();


        mPrefs = activity.getPreferences(context.MODE_PRIVATE);


    }

    public void saveURL(String urls) {


        SharedPreferences.Editor prefsEditor = mPrefs.edit();

        prefsEditor.putString(SAVE, urls);

        prefsEditor.apply();
    }
    public String geturl() {

        String STR = mPrefs.getString(SAVE, "");
        return STR;
    }

}
