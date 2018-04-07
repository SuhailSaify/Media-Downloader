package com.example.suhail.videodownloader;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Suhail on 4/7/2018.
 */

public class ShredPref extends MainActivity {


    public static final String KEY_SHOWALERT = "alert";
    Context context;
    SharedPreferences sharedPreferences;

    //Editor for shared preferences..
    SharedPreferences.Editor editor;


    public ShredPref(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void put(boolean b) {
        editor.putBoolean(KEY_SHOWALERT, b);
        editor.apply();

    }
    public boolean showalert() {

        if(sharedPreferences!=null)
        {
            return sharedPreferences.getBoolean(KEY_SHOWALERT,false);
        }
        return false;

    }
    }

