package com.example.filesmanager.videodownloader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ActivityHelp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(ActivityHelp.this, MainActivity.class));
        super.onBackPressed();
    }
}
