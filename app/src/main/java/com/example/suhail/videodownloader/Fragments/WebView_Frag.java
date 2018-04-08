package com.example.suhail.videodownloader.Fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.suhail.videodownloader.MainActivity;
import com.example.suhail.videodownloader.R;


public class WebView_Frag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    ImageView imageView;
    EditText url;
    Button go;
    WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_view_, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = getActivity().findViewById(R.id.goback);
        go = getActivity().findViewById(R.id.gobutton);
        url = getActivity().findViewById(R.id.url_edittext);
        webView = getActivity().findViewById(R.id.web_view);
        webView.setWebViewClient(new MyBrowser());
        webView.loadUrl("https://www.google.com");
    /*    webView.setWebChromeClient(new WebChromeClient(){

          public void onProgressChanged(WebView view, int progress) {
                getActivity().setTitle("Loading...");
                getActivity().setProgress(progress * 100);
                if(progress == 100)
                    getActivity().setTitle("My title");
            }
        });
  */
        listner();

    }

    private void listner() {

        imageView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        webView.goBack();
                    }
                }
        );

        go.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String url1 = url.getText().toString();

                        webView.getSettings().setLoadsImagesAutomatically(true);
                        webView.getSettings().setJavaScriptEnabled(true);
                        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                        if (url1.contains("https://")) {
                            webView.loadUrl(url1);
                        } else {
                            webView.loadUrl("https://" + url1);
                        }

                    }
                }
        );
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
                Toast.makeText(getActivity(), "Downloading Video", Toast.LENGTH_SHORT).show();
                MainActivity activity = (MainActivity) getActivity();
                activity.download(url);
            } else {
                view.loadUrl(url);
            }
            return true;
        }


    }

}



