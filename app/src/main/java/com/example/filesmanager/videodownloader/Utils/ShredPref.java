package com.example.filesmanager.videodownloader.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.filesmanager.videodownloader.MainActivity;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Suhail on 4/7/2018.
 */

public class ShredPref extends MainActivity {


    public static final String KEY_SHOWALERT = "alert";
    Context context;
    SharedPreferences sharedPreferences;
    // ArrayList<Integer> progress=new ArrayList<>(Collections.nCopies(60, 0));
    //Editor for shared preferences..
    SharedPreferences.Editor editor;


    public ShredPref(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();

    }

    public void put(int b, int position) {
        editor.putInt(String.valueOf(position), b);
        editor.apply();

    }

    public void putbutton(String text,int position)
    {
        editor.putString(String.valueOf(1000-position),text);
        editor.apply();

    }
    public int getprogress(int position) {


        return sharedPreferences.getInt(String.valueOf(position), 0);


    }

    public String getbutton(int position) {

        return sharedPreferences.getString(String.valueOf(1000-position), "START");


    }
}

