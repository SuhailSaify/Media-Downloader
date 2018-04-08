package com.example.suhail.videodownloader.Fragments;


import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.suhail.videodownloader.Adapters.DownloadListAdapter;
import com.example.suhail.videodownloader.Model.Urls;
import com.example.suhail.videodownloader.R;
import com.example.suhail.videodownloader.Utils.DownloadedVidShared;
import com.example.suhail.videodownloader.Utils.ShredPref;
import com.example.suhail.videodownloader.Utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadFrag extends Fragment {


    RecyclerView recyclerView;
    TextView pasteanotherurl;
    ArrayList<Urls> mainMenuItems = new ArrayList<>();
    DownloadListAdapter downloadListAdapter;
    SharedPreferences mPrefs;
    Gson gson = new Gson();
    Type listOfObjects;
    DownloadedVidShared downloadedVidShared;

    public DownloadFrag() {
        // Required empty public constructor
    }


    void check() {
        ShredPref shredPref = new ShredPref(getContext());
        if (shredPref.showalert()) {
            // Toast.makeText(getContext(), String.valueOf(shredPref.showalert()), Toast.LENGTH_SHORT).show();
            showalert();
            shredPref.put(false);
        }
        check();
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
        downloadListAdapter = new DownloadListAdapter(mainMenuItems, getContext(), getActivity(), Utils.getRootDirPath(getActivity()));

        recyclerView.setAdapter(downloadListAdapter);


//-----------------------

        pasteanotherurl = getActivity().findViewById(R.id.pasteanotherurl);


        //-----------------------------

        Listners();
        //  check();

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

    public void setRecyclerView(String uurl) {


        mainMenuItems.add(new Urls(uurl, 0));
        downloadListAdapter.notifyDataSetChanged();

        downloadedVidShared.saveURL(mainMenuItems);
        String strObject = gson.toJson(mainMenuItems, listOfObjects); // Here list is your List<CUSTOM_CLASS> object
        SharedPreferences.Editor prefsEditor = mPrefs.edit();

        prefsEditor.putString("MyList", strObject);

        prefsEditor.apply();


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



