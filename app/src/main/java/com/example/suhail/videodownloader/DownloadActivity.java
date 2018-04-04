package com.example.suhail.videodownloader;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.suhail.videodownloader.Model.Urls;
import com.example.suhail.videodownloader.adapter.DownloadListAdapter;
import com.example.suhail.videodownloader.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DownloadActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView pasteanotherurl;
    ArrayList<Urls> mainMenuItems = new ArrayList<>();
    DownloadListAdapter downloadListAdapter;
    SharedPreferences mPrefs;
    Gson gson = new Gson();
    Type listOfObjects;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        boolean s = getIntent().getBooleanExtra("ViewOnly",false);





        mPrefs = getPreferences(MODE_PRIVATE);
        listOfObjects = new TypeToken<ArrayList<Urls>>() {
        }.getType();
        String json = mPrefs.getString("MyList", "");
        if(gson.fromJson(json, listOfObjects)!=null) {
            mainMenuItems = gson.fromJson(json, listOfObjects);
        }
        recyclerView = findViewById(R.id.download_list_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DownloadActivity.this,
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        downloadListAdapter = new DownloadListAdapter(mainMenuItems, DownloadActivity.this, Utils.getRootDirPath(getApplicationContext()));

        recyclerView.setAdapter(downloadListAdapter);

        if(!s) {
            showalert();
        }
//-----------------------

        pasteanotherurl = findViewById(R.id.pasteanotherurl);


        //-----------------------------

        Listners();

    }

    private void Listners() {

        pasteanotherurl.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showalert();

                    }
                }
        );
    }

    void setRecyclerView(String uurl) {


        mainMenuItems.add(new Urls(uurl, 0));
        downloadListAdapter.notifyDataSetChanged();


        String strObject = gson.toJson(mainMenuItems, listOfObjects); // Here list is your List<CUSTOM_CLASS> object
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("MyList", strObject);
        prefsEditor.apply();


    }

    void showalert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(DownloadActivity.this);

        alert.setTitle("Download Video");
        alert.setMessage("Paste Url");

// Set an EditText view to get user input
        final EditText input = new EditText(DownloadActivity.this);
        alert.setView(input);
        input.setPadding(10, 10, 10, 10);


        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {


                setRecyclerView(input.getText().toString());


            }

        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();


    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();

        startActivity(new Intent(DownloadActivity.this, MainActivity.class));
    }
}
