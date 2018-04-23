package com.example.filesmanager.videodownloader.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.filesmanager.videodownloader.MainActivity;

public class DoNotShowAgain extends MainActivity {

    Boolean show;
    String KEY = "KEYBO";
    Context context;

    SharedPreferences sharedPreferences;

    SharedPreferences.Editor editor;


    public void setShow(Boolean show) {


        editor.putBoolean(KEY, show);
        editor.apply();


    }

    public Boolean getShow() {

        return sharedPreferences.getBoolean(KEY, true);
    }

    public DoNotShowAgain(Context context) {

        this.context = context;

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }
}
