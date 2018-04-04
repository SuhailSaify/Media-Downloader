package com.example.suhail.videodownloader;

import android.Manifest;
import android.app.Activity;
import android.app.AutomaticZenRule;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;

import com.example.suhail.videodownloader.utils.Utils;

public class MainActivity extends Activity {

    LinearLayout view_downloads;
    LinearLayout pasteurl;
    Context context;
    android.app.DownloadManager downloadManager;
    SharedPreferences preferenceManager;
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1;
    String dirPath;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        isStoragePermissionGranted();
        isStoragePermissionGrantedRead();

        context = MainActivity.this;


        view_downloads = findViewById(R.id.view_downloads);
        view_downloads.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(MainActivity.this, DownloadActivity.class) ;
                        intent.putExtra("ViewOnly",true) ;
                        startActivity(intent);
                        finish();
                    }
                }
        );

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        final String imageURL = "http://mirrors.standaloneinstaller.com/video-sample/jellyfish-25-mbps-hd-hevc.3gp";


        pasteurl = findViewById(R.id.Paste_url);
        pasteurl.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        startActivity(new Intent(MainActivity.this, DownloadActivity.class));


                    }
                }
        );

        dirPath = Utils.getRootDirPath(getApplicationContext());

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
            // other 'case' lines to check for other
            // permissions this app might request
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