package com.example.suhail.videodownloader;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;


import java.io.File;

import com.example.suhail.videodownloader.Fragments.DownloadFrag;
import com.example.suhail.videodownloader.Fragments.MainFrag;
import com.example.suhail.videodownloader.Fragments.ShowDownloaded;
import com.example.suhail.videodownloader.Fragments.SocialMediaFrag;
import com.example.suhail.videodownloader.Fragments.WebView_Frag;
import com.example.suhail.videodownloader.Utils.Utils;

public class MainActivity extends FragmentActivity {



    FrameLayout main_layout;
    FrameLayout download_layout;
    DownloadFrag downloadFrag;
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

        main_layout = findViewById(R.id.main_container);
        download_layout = findViewById(R.id.download_container);

        isStoragePermissionGranted();
        isStoragePermissionGrantedRead();

        // context = MainActivity.this;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        final String imageURL = "http://mirrors.standaloneinstaller.com/video-sample/jellyfish-25-mbps-hd-hevc.3gp";
        dirPath = Utils.getRootDirPath(getApplicationContext());

        if (isStoragePermissionGranted() && isStoragePermissionGrantedRead()) {
            downloadFrag = new DownloadFrag();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.download_container, downloadFrag)

                    .commit();


            getSupportFragmentManager().beginTransaction()

                    .add(R.id.main_container, new MainFrag())
                    .commit();

            download_layout.setVisibility(View.INVISIBLE);

        }


    }


    public void download(String url) {

        downloadFrag.setRecyclerView(url);

    }


    public void checkWriteExternalStoragePermission() {
        final int MY_PERMISSIONS_REQUEST_PHONE_CALL = 1;
        ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((MainActivity) context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
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
                }
                return;

            }

        }
    }


    public void Openfile(String filename) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator +
                filename);
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);

        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String ext = file.getName().substring(file.getName().indexOf(".") + 1);
        String type = mime.getMimeTypeFromExtension(ext);

        intent.setDataAndType(Uri.fromFile(file), type);

        context.startActivity(intent);


    }

    @Override
    public void onBackPressed() {


        Fragment f = getSupportFragmentManager().findFragmentById(R.id.main_container);


        if (download_layout.getVisibility() == View.VISIBLE)
        // do something with f
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new MainFrag());
            download_layout.setVisibility(View.INVISIBLE);
            main_layout.setVisibility(View.VISIBLE);

        }

        else if (f instanceof SocialMediaFrag)

        {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new MainFrag()).commit();

        } else if (f instanceof ShowDownloaded)

        {

            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new MainFrag()).commit();

        } else if (f instanceof WebView_Frag) {


            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

            alert.setTitle("Exit to main manu");
            //alert.setMessage("Exit");

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
        // Toast.makeText(getApplicationContext(), "destroyed", Toast.LENGTH_SHORT).show();
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

