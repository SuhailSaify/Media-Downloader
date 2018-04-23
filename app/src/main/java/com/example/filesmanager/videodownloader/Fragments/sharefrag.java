package com.example.filesmanager.videodownloader.Fragments;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.filesmanager.videodownloader.MainActivity;
import com.example.filesmanager.videodownloader.R;
import com.example.filesmanager.videodownloader.Utils.DoNotShowAgain;

/**
 * A simple {@link Fragment} subclass.
 */
public class sharefrag extends Fragment {

    Context context;
    DoNotShowAgain doNotShowAgain;
    TextView share;
    TextView rate;
    TextView cancl;
    CheckBox radioButton;

    MainActivity mainActivity;

    public sharefrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sharefrag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        context = getActivity();


        if (Build.VERSION.SDK_INT >= 21)


        {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.cardview_dark_background));

        }


        doNotShowAgain = new DoNotShowAgain(getContext());

        share = getActivity().findViewById(R.id.shareapp1);
        rate = getActivity().findViewById(R.id.rateapp1);
        cancl = getActivity().findViewById(R.id.cancelrateshare1);
        radioButton = getActivity().findViewById(R.id.notshowradio1);
        Listneres();

    }

    private void Listneres() {

        share.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareapp();
                    }
                }
        );

        rate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rateapp();
                    }
                }
        );
        cancl.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FragmentManager fragmentManager2 = getFragmentManager();
                        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                        MainFrag fragment2 = new MainFrag();
                        fragmentTransaction2.replace(R.id.main_container, fragment2).commit();
                        // startActivity(new Intent(context, MainActivity.class));
                        //getActivity().finish();

                    }
                }
        );

        radioButton.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        //doNotShowAgain.setShow(isChecked);

                       // Toast.makeText(context, "value" + String.valueOf(isChecked), Toast.LENGTH_LONG).show();

                        if (String.valueOf(isChecked) != null) {
                            doNotShowAgain.setShow(!isChecked);
                        }//  mainActivity.putshow(isChecked);
                    }
                }
        );


    }


    void rateapp() {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }

    void shareapp() {


        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "All File Downloader");
            String sAux = "\nLet me recommend you this application\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=" + context.getPackageName() + "\n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "Share on"));
        } catch (Exception e) {
            Toast.makeText(context, "Some Error Accrued", Toast.LENGTH_SHORT).show();
            //e.toString();

        }


    }


}
