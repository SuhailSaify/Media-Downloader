package com.example.filesmanager.videodownloader.Fragments;


import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.TextView;



import com.example.filesmanager.videodownloader.NoConnectionActivity;
import com.example.filesmanager.videodownloader.R;

import com.example.filesmanager.videodownloader.Utils.DoNotShowAgain;
import com.google.gson.Gson;


import java.lang.reflect.Type;


/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadFrag extends Fragment {


    //<!--------------------------------------------------Dec------------------------------------------
    DownloadManager downloadManager;

    DoNotShowAgain doNotShowAgain;
    String url123;
    RecyclerView recyclerView;
    TextView pasteanotherurl;

    SharedPreferences mPrefs;
    Gson gson = new Gson();
    Type listOfObjects;


//--------------------------------------------------------------!>


    public DownloadFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_download2, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        /*<------------------------------------------------------------------------------------------------
        downloadedVidShared = new DownloadedVidShared(getContext(), getActivity());
        mPrefs = getActivity().getPreferences(getActivity().MODE_PRIVATE);
        listOfObjects = new TypeToken<ArrayList<Urls>>() {
        }.getType();
        String json = mPrefs.getString("MyList", "");


        if (downloadedVidShared.geturl() != null) {
            mainMenuItems = downloadedVidShared.geturl();
        }
        recyclerView = getActivity().findViewById(R.id.download_list_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        downloadListAdapter = new DownloadListAdapter(mainMenuItems, getContext(), getActivity(), Environment.DIRECTORY_DOWNLOADS);

        recyclerView.setAdapter(downloadListAdapter);



        pasteanotherurl = getActivity().findViewById(R.id.pasteanotherurl);
----------------------------------------------------------------------------------!>*/

        //   Listners();

    }




   /* public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 42 && resultCode == getActivity().RESULT_OK) {

            String folderLocation = intent.getExtras().getString("data");
            Log.i("folderLocation", folderLocation);


            mainMenuItems.add(new Urls(url123, 0, false, folderLocation));
            downloadListAdapter.notifyDataSetChanged();

            downloadedVidShared.saveURL(mainMenuItems);
            String strObject = gson.toJson(mainMenuItems, listOfObjects); // Here list is your List<CUSTOM_CLASS> object
            SharedPreferences.Editor prefsEditor = mPrefs.edit();

            prefsEditor.putString("MyList", strObject);

            prefsEditor.apply();
            url123 = null;

        }
    }
*/

    /*  private void Listners() {

          pasteanotherurl.setOnClickListener(
                  new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                          showalert();

                      }
                  }
          );
      }

  */
    public void setRecyclerView(String uurl) {


        Boolean isConnected;

        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {


            url123 = uurl;

            downloadManager = (DownloadManager) getActivity().getSystemService(getContext().DOWNLOAD_SERVICE);

            //  Toast.makeText(getActivity(), "called", Toast.LENGTH_SHORT).show();
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
            startActivity(new Intent(getActivity(), NoConnectionActivity.class));
        }
    }

    void showalert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle("Download Video");
        alert.setMessage("Paste Url");

// Set an EditText view to get user input
        final EditText input = new EditText(getActivity());
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


}



