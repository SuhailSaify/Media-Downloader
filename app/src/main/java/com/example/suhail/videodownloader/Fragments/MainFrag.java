package com.example.suhail.videodownloader.Fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.suhail.videodownloader.MainActivity;
import com.example.suhail.videodownloader.R;
import com.example.suhail.videodownloader.Utils.ShredPref;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFrag extends Fragment {
    LinearLayout view_downloads;
    LinearLayout pasteurl;
    FrameLayout main_layout;
    FrameLayout download_layout;
    LinearLayout webview;
    MainActivity mainActivity;

    public MainFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        main_layout = getActivity().findViewById(R.id.main_container);
        download_layout = getActivity().findViewById(R.id.download_container);



        view_downloads = getActivity().findViewById(R.id.view_downloads);
        view_downloads.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // ShredPref shredPref = new ShredPref(getContext());
                        // shredPref.put(false);

                        main_layout.setVisibility(View.VISIBLE);

                        download_layout.setVisibility(View.INVISIBLE);

                        FragmentManager fragmentManager2 = getFragmentManager();
                        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                        ShowDownloaded fragment2 = new ShowDownloaded();
                        fragmentTransaction2.replace(R.id.main_container, fragment2).commit();


                    }
                }
        );

        pasteurl = getActivity().findViewById(R.id.Paste_url);
        pasteurl.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // ShredPref shredPref = new ShredPref(getContext());
                        //shredPref.put(true);

                        main_layout.setVisibility(View.INVISIBLE);

                        download_layout.setVisibility(View.VISIBLE);

                    }
                }
        );

        webview=getActivity().findViewById(R.id.webview_layout);
        webview.setOnClickListener(
                new
                        View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                main_layout.setVisibility(View.VISIBLE);

                                download_layout.setVisibility(View.INVISIBLE);

                                FragmentManager fragmentManager2 = getFragmentManager();
                                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                                WebView_Frag fragment2 = new WebView_Frag();
                                fragmentTransaction2.replace(R.id.main_container, fragment2).commit();

                            }
                        }
        );

    }
}
