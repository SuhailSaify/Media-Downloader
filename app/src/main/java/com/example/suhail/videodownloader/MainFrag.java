package com.example.suhail.videodownloader;



import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFrag extends Fragment {
    LinearLayout view_downloads;
    LinearLayout pasteurl;
    FrameLayout main_layout;
    FrameLayout download_layout;
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

        main_layout=getActivity().findViewById(R.id.main_container);
        download_layout=getActivity().findViewById(R.id.download_container);

        view_downloads = getActivity().findViewById(R.id.view_downloads);
        view_downloads.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ShredPref shredPref= new ShredPref(getContext());
                        shredPref.put(false);

                    main_layout.setVisibility(View.INVISIBLE);
                    download_layout.setVisibility(View.VISIBLE);

                    }
                }
        );

        pasteurl = getActivity().findViewById(R.id.Paste_url);
        pasteurl.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ShredPref shredPref= new ShredPref(getContext());
                        shredPref.put(true);

                        main_layout.setVisibility(View.INVISIBLE);
                        download_layout.setVisibility(View.VISIBLE);

                    }
                }
        );


    }
}
