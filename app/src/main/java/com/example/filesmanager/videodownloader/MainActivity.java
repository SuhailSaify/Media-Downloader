package com.example.filesmanager.videodownloader;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.FrameLayout;
import android.widget.Toast;


import com.example.filesmanager.videodownloader.Fragments.ShowDownloads;
import com.example.filesmanager.videodownloader.Fragments.MainFrag;
import com.example.filesmanager.videodownloader.Fragments.SocialMediaFrag;
import com.example.filesmanager.videodownloader.Fragments.WebView_Frag;
import com.example.filesmanager.videodownloader.Fragments.sharefrag;
import com.example.filesmanager.videodownloader.Utils.DoNotShowAgain;
import com.example.filesmanager.videodownloader.Utils.Utils;

public class MainActivity extends FragmentActivity {


    DoNotShowAgain doNotShowAgain;
    FrameLayout main_layout;
    FrameLayout download_layout;
    DownloadManager downloadManager;
    Context context;
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1;
    String dirPath;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        context = MainActivity.this;


        Log.d("HERE", Utils.getRootDirPath(getApplicationContext()));

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            startActivity(new Intent(MainActivity.this, NoConnectionActivity.class));
        }

        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        main_layout = findViewById(R.id.main_container);


        isStoragePermissionGranted();
        isStoragePermissionGrantedRead();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        dirPath = Utils.getRootDirPath(getApplicationContext());


        doNotShowAgain = new DoNotShowAgain(this);
        Broadcast();
        startfrag();


    }


    public void startfrag() {



        getSupportFragmentManager().beginTransaction()

                .add(R.id.main_container, new MainFrag())
                .commit();



    }




    private void Broadcast() {

        BroadcastReceiver onComplete = new BroadcastReceiver() {
            public void onReceive(Context ctxt, Intent intent) {

                if (doNotShowAgain.getShow()) {


                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new sharefrag()).commit();



                }

            }
        };

        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public void download(String uurl) {

        Boolean isConnected;

        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {


            Uri uri = Uri.parse(uurl);
            String name = URLUtil.guessFileName(uurl, null, null);
            DownloadManager.Request req = new DownloadManager.Request(uri);

            req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                    | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(true)
                    .setTitle(name)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDescription("Downloading File")
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                            name);
            downloadManager.enqueue(req);

        } else {
            startActivity(new Intent(MainActivity.this, NoConnectionActivity.class));
        }

    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "If permission denied, restart APP & Please Grant Permission To Continue", Toast.LENGTH_SHORT).show();


                }
                return;

            }

        }
    }



    @Override
    public void onBackPressed() {


        Fragment f = getSupportFragmentManager().findFragmentById(R.id.main_container);

/*
        if (download_layout.getVisibility() == View.VISIBLE)
        // do something with f
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new MainFrag());
            download_layout.setVisibility(View.INVISIBLE);
            main_layout.setVisibility(View.VISIBLE);

        } else */if (f instanceof SocialMediaFrag)

        {
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

            alert.setTitle("Exit to main manu");


// Set an EditText view to get user input
            alert.setPositiveButton("Go Back", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {


                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new MainFrag()).commit();

                }

            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Canceled.

                }
            });

            alert.show();

        } else if (f instanceof WebView_Frag) {


            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

            alert.setTitle("Exit to main manu");


// Set an EditText view to get user input
            alert.setPositiveButton("Go Back", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {


                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new MainFrag()).commit();

                }

            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {


                }
            });

            alert.show();


        } else if (f instanceof ShowDownloads) {


            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new MainFrag()).commit();


        } else {

            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            super.onBackPressed();

        }
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onPause() {

        super.onPause();
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("one", "Permission is granted");
                return true;
            } else {

                Log.v("one", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("one", "Permission is granted");
            return true;
        }
    }

    public boolean isStoragePermissionGrantedRead() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("one", "Permission is granted");
                return true;
            } else {

                Log.v("one", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("one", "Permission is granted");
            return true;
        }
    }

}

/*
created by Suhail Saifi
Suhail.14298@gmail.com

 */
