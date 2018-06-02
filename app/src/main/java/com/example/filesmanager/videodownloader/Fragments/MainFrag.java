package com.example.filesmanager.videodownloader.Fragments;


import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.filesmanager.videodownloader.ActivityHelp;
import com.example.filesmanager.videodownloader.ApiClient.ApiClient;
import com.example.filesmanager.videodownloader.Interfaces.ApiInterface;
import com.example.filesmanager.videodownloader.MainActivity;
import com.example.filesmanager.videodownloader.NoConnectionActivity;
import com.example.filesmanager.videodownloader.R;

import com.example.filesmanager.videodownloader.Utils.DoNotShowAgain;
import com.example.filesmanager.videodownloader.model.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFrag extends Fragment {

    /*
    <!---------------------------------------declaration-----------------------------------------------------------------
     */
    ArrayList<Example> arrayList;
    DoNotShowAgain doNotShowAgain;
    LinearLayout share_app;
    LinearLayout rate_app;
    LinearLayout linearLayout_help;
    LinearLayout socialMedia;
    LinearLayout view_downloads;
    LinearLayout pasteurl;
    FrameLayout main_layout;
    FrameLayout download_layout;
    LinearLayout webview;
    MainActivity mainActivity;
    DownloadManager downloadManager;
    Context context;
    /*
    ---------------------------------------------------------------------!>
     */

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

        context = getContext();
        doNotShowAgain = new DoNotShowAgain(context);
        share_app = getActivity().findViewById(R.id.share_main);
        rate_app = getActivity().findViewById(R.id.rate_main);
        main_layout = getActivity().findViewById(R.id.main_container);
        download_layout = getActivity().findViewById(R.id.download_container);
        linearLayout_help = getActivity().findViewById(R.id.help);
        socialMedia = getActivity().findViewById(R.id.social_media_download);
        view_downloads = getActivity().findViewById(R.id.view_downloads);
        pasteurl = getActivity().findViewById(R.id.Paste_url);
        webview = getActivity().findViewById(R.id.webview_layout);

        Listeners();

    }


    private void Listeners() {


        rate_app.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        rateapp();


                    }
                }
        );


        share_app.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        shareapp();

                    }
                }
        );


        linearLayout_help.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startActivity(new Intent(getActivity(), ActivityHelp.class));

                    }
                }
        );

        socialMedia.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FragmentManager fragmentManager2 = getFragmentManager();
                        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                        SocialMediaFrag fragment2 = new SocialMediaFrag();
                        fragmentTransaction2.replace(R.id.main_container, fragment2).commit();

                    }
                }
        );


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
                        DownFragment fragment2 = new DownFragment();
                        fragmentTransaction2.replace(R.id.main_container, fragment2).commit();


                    }
                }
        );


        pasteurl.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Boolean isConnected;

                        ConnectivityManager cm =
                                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                        isConnected = activeNetwork != null &&
                                activeNetwork.isConnectedOrConnecting();

                        if (isConnected) {
                            showalert();

                        } else {
                            startActivity(new Intent(getActivity(), NoConnectionActivity.class));
                        }


                        // main_layout.setVisibility(View.INVISIBLE);
                        //download_layout.setVisibility(View.VISIBLE);
                       /* FragmentManager fragmentManager2 = getFragmentManager();
                        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                        DownloadFrag fragment2 = new DownloadFrag();
                        fragmentTransaction2.replace(R.id.main_container, fragment2).commit();
*/

                    }
                }
        );


        webview.setOnClickListener(
                new
                        View.OnClickListener() {
                            @Override
                            public void onClick(View view) {








                                Boolean isConnected;

                                ConnectivityManager cm =
                                        (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                                isConnected = activeNetwork != null &&
                                        activeNetwork.isConnectedOrConnecting();

                                if (isConnected) {
                                    main_layout.setVisibility(View.VISIBLE);

                                    download_layout.setVisibility(View.INVISIBLE);

                                    FragmentManager fragmentManager2 = getFragmentManager();
                                    FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                                    WebView_Frag fragment2 = new WebView_Frag();
                                    fragmentTransaction2.replace(R.id.main_container, fragment2).commit();


                                } else {
                                    startActivity(new Intent(getActivity(), NoConnectionActivity.class));
                                }

                            }
                        }
        );
    }


    void getvideofromurl(String url)
    {

        final ApiInterface apiservice = ApiClient.getClient().create(ApiInterface.class);

        String currenturl = "https://www.youtube.com/watch?v=aJOTlE1K90k";



        Call <Example> call=apiservice.getvideo(currenturl,"json");
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {

                Example example=response.body();

                for(int i=0;i<example.getUrls().size();++i)

                {
                    Log.d("response",example.getUrls().get(i).getId());
                    Log.d("label",example.getUrls().get(i).getLabel());
                }

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

                Log.d("failed",t.getMessage());
            }
        });


    }

    void ShowRateShareAPP() {

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle("Rate/Share");


// Set an EditText view to get user input

        alert.setPositiveButton("Rate this app", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                rateapp();

            }
        });
        alert.setPositiveButton("Share", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                shareapp();
            }

        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();


    }


    void rateapp() {
        Uri uri = Uri.parse("market://details?id=" + getContext().getPackageName());
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
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getContext().getPackageName())));
        }
    }

    void shareapp() {


        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "All File Downloader");
            String sAux = "\nLet me recommend you this application\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=" + getContext().getPackageName() + "\n\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "Share on"));
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Some Error Accrued", Toast.LENGTH_SHORT).show();
            //e.toString();

        }


    }


    void showalert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

        alert.setTitle("Download File");
        alert.setMessage("Paste Url");

// Set an EditText view to get user input
        final EditText input = new EditText(getActivity());
        input.setPadding(10, 10, 10, 10);
        alert.setView(input);
        input.setPadding(10, 10, 10, 10);


        alert.setPositiveButton("Download", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                final String uurl = input.getText().toString().trim();

                download(uurl);


            }

        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();


    }

    public void download(String uurl) {
        if (uurl != null) {

            Log.d("URL NOT VALID", String.valueOf(URLUtil.isValidUrl(uurl)));

            if (URLUtil.isValidUrl(uurl)) {


                downloadManager = (DownloadManager) getActivity().getSystemService(getContext().DOWNLOAD_SERVICE);


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
                Toast.makeText(getContext(), "Invalid Url", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Invalid Url", Toast.LENGTH_SHORT).show();
        }
    }

}
