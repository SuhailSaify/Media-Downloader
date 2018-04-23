package com.example.filesmanager.videodownloader.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.filesmanager.videodownloader.MainActivity;
import com.example.filesmanager.videodownloader.NoConnectionActivity;
import com.example.filesmanager.videodownloader.R;


public class SocialMediaFrag extends Fragment {


    ImageView back;
    String url = "https://en.savefrom.net/";
    WebView webView;
    ProgressBar progressBar;


    public SocialMediaFrag() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Boolean isConnected;

        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {


            back = getActivity().findViewById(R.id.goback_socialmedia);
            webView = getActivity().findViewById(R.id.web_view_socailmedia);
            progressBar = getActivity().findViewById(R.id.pg_bar_social_media);

            if (webView != null) {
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setAllowFileAccess(true);
                webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                webView.getSettings().setLoadWithOverviewMode(true);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.loadUrl(url);
                webView.setWebViewClient(new SocialMediaFrag.MyBrowser());

                webView.setWebChromeClient(new WebChromeClient() {

                    public void onProgressChanged(WebView view, int progress) {

                        if (progressBar != null) {
                            progressBar.setProgress(progress);

                            if (progress == 100) {

                                progressBar.setVisibility(View.GONE);

                            } else {
                                progressBar.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });


                webView.setDownloadListener(
                        new DownloadListener() {
                            @Override
                            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                                MainActivity activity = (MainActivity) getActivity();
                                activity.download(url);
                                Toast.makeText(getContext(), "Downloading", Toast.LENGTH_SHORT).show();

                            }
                        }
                );

            } else {
                Toast.makeText(getActivity(), "SOME ERROR ACCURED ", Toast.LENGTH_SHORT).show();

            }


        } else {
            startActivity(new Intent(getActivity(), NoConnectionActivity.class));
        }

        Listener();
    }

    private void Listener() {

        if (back != null)

        {
            back.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            FragmentManager fragmentManager2 = getFragmentManager();
                            FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                            MainFrag fragment2 = new MainFrag();
                            fragmentTransaction2.replace(R.id.main_container, fragment2).commit();

                        }
                    }

            );
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_socail_media, container, false);
    }

    class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains(".mp4") || url.contains(".webm")
                    || url.contains(".flv") || url.contains(".f4v")
                    || url.contains(".f4p") || url.contains(".f4a")
                    || url.contains(".g3p") || url.contains(".m4v")
                    || url.contains(".f4b") || url.contains(".mpg")
                    || url.contains(".mpeg") || url.contains(".m2v")
                    || url.contains(".mpg")
                    || url.contains(".mp2") || url.contains(".mpeg")
                    || url.contains(".mpe") || url.contains(".mpv")
                    || url.contains(".amv") || url.contains(".m4v")
                    || url.contains(".wmv")
                    || url.contains(".wmv") || url.contains(".wmv")

                    ) {
                Toast.makeText(getActivity(), "Downloading File", Toast.LENGTH_SHORT).show();
                MainActivity activity = (MainActivity) getActivity();
                activity.download(url);
            } else {
                view.loadUrl(url);
            }
            return true;
        }


    }

}
