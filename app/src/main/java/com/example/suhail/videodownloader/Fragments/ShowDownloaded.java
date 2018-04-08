package com.example.suhail.videodownloader.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.suhail.videodownloader.Adapters.DownloadListAdapter;
import com.example.suhail.videodownloader.Adapters.ShowDownloadedAdapter;
import com.example.suhail.videodownloader.Model.DownloadedVideos;
import com.example.suhail.videodownloader.Model.Urls;
import com.example.suhail.videodownloader.R;
import com.example.suhail.videodownloader.Utils.DownloadedVidShared;
import com.example.suhail.videodownloader.Utils.Utils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowDownloaded extends Fragment {

    FrameLayout main_layout;
    FrameLayout download_layout;
    RecyclerView recyclerView;
    ArrayList<DownloadedVideos> downloadedVideos = new ArrayList<>();
    DownloadedVidShared downloadedVidShared;
    ShowDownloadedAdapter showDownloadedAdapter;

    public ShowDownloaded() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_downloaded, container, false);




    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        main_layout = getActivity().findViewById(R.id.main_container);
        download_layout = getActivity().findViewById(R.id.download_container);
      //  main_layout.setVisibility(View.VISIBLE);
       // download_layout.setVisibility(View.INVISIBLE);


        recyclerView = getActivity().findViewById(R.id.show_downloaded_recyler_view);
        downloadedVidShared = new DownloadedVidShared(getContext(), getActivity());

        downloadedVideos = downloadedVidShared.getvid();

        if(downloadedVideos==null)
        {
            Toast.makeText(getContext(), "nul", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getContext(), "notnull", Toast.LENGTH_SHORT).show();
        }


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        showDownloadedAdapter = new ShowDownloadedAdapter(downloadedVideos, getContext(), getActivity());

        recyclerView.setAdapter(showDownloadedAdapter);

    }
}
