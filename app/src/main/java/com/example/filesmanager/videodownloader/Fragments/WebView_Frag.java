package com.example.filesmanager.videodownloader.Fragments;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.renderscript.ScriptGroup;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.filesmanager.videodownloader.ApiClient.ApiClient;
import com.example.filesmanager.videodownloader.Interfaces.ApiInterface;
import com.example.filesmanager.videodownloader.MainActivity;
import com.example.filesmanager.videodownloader.R;
import com.example.filesmanager.videodownloader.Utils.SavelastUrl;
import com.example.filesmanager.videodownloader.model.Example;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WebView_Frag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    AppCompatImageView checkforvideo;
    ImageView imageView;
    Button go;
    SavelastUrl savelastUrl;
    WebView webView;
    ProgressBar progressBar;
    SearchView searchView;
    Example example;
    ProgressDialog progressl;
    ProgressDialog progressDialog2;
    private Uri Download_Uri;
    DownloadManager downloadManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_view_, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //<!-------------------------------------------------------------------------------------------------
        downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        progressl = new ProgressDialog(getActivity());
        progressDialog2 = new ProgressDialog(getActivity());
        checkforvideo = getActivity().findViewById(R.id.checkforvideo);
        progressBar = getActivity().findViewById(R.id.web_progressbar);
        savelastUrl = new SavelastUrl(getContext(), getActivity());
        searchView = getActivity().findViewById(R.id.searchvew);
        String s = savelastUrl.geturl();
        imageView = getActivity().findViewById(R.id.goback);
        go = getActivity().findViewById(R.id.gobutton);
        webView = getActivity().findViewById(R.id.web_view);
        webView.setWebViewClient(new MyBrowser());
        checkforvideo.setEnabled(true);


//----------------------------------!>

        if (s.contains(".com"))

        {

            webView.loadUrl(s);

            searchView.setQuery(s, false);

        } else {

            webView.loadUrl("https://www.google.com");
        }
        webView.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(progress);

                if (progress == 100) {


                    progressBar.setVisibility(View.GONE);
                    savelastUrl.saveURL(webView.getUrl());
                    checkforvideo.setEnabled(true);
                    if (webView.getUrl() != null)

                    {
                        searchView.setQuery(webView.getUrl(), false);
                    }
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });


        listner();

    }

    private void listner() {


        checkforvideo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String urlcurrent = webView.getUrl();

                        if (urlcurrent.contains("youtube") || urlcurrent.contains("youtu.be")) {

                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                            alert.setCancelable(true);
                            alert.setTitle("Cannot Download from Youtube");
                            alert.setMessage("try another source");

                            alert.setPositiveButton("back", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                }

                            });


                            alert.show();


                        } else {
                            getvideofromurl(urlcurrent);
                        }

                    }
                }
        );


        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {

                        String url1 = searchView.getQuery().toString();

                        webView.getSettings().setLoadsImagesAutomatically(true);
                        webView.getSettings().setJavaScriptEnabled(true);
                        webView.getSettings().setJavaScriptEnabled(true);
                        webView.getSettings().setLoadWithOverviewMode(true);
                        webView.getSettings().setUseWideViewPort(true);
                        webView.getSettings().setAllowFileAccess(true);
                        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                        if (url1.contains("https://")) {
                            webView.loadUrl(url1);
                            searchView.setQuery(url1, false);
                        } else if (url1.contains("www.") && url1.contains(".com")) {
                            webView.loadUrl("https://" + url1);
                            searchView.setQuery(url1, false);
                        } else if (url1.contains(".com")) {
                            webView.loadUrl("https://www." + url1);
                            searchView.setQuery(url1, false);
                        } else {
                            webView.loadUrl("https://www.google.com/search?q=" + url1);
                            searchView.setQuery(url1, false);
                        }

                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {

                        return false;
                    }
                }
        );

        searchView.setOnSearchClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                    }
                }
        );


        webView.setDownloadListener(
                new DownloadListener() {
                    @Override
                    public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
                        Toast.makeText(getActivity(), "Downloading File", Toast.LENGTH_SHORT).show();
                        MainActivity activity = (MainActivity) getActivity();
                        activity.download(s);
                    }
                }
        );


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

                        String url1 = searchView.getQuery().toString();

                        webView.getSettings().setLoadsImagesAutomatically(true);
                        webView.getSettings().setJavaScriptEnabled(true);
                        webView.getSettings().setJavaScriptEnabled(true);
                        webView.getSettings().setAllowFileAccess(true);
                        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                        webView.getSettings().setLoadWithOverviewMode(true);
                        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                        if (url1.contains("https://")) {
                            webView.loadUrl(url1);
                        } else if

                                (url1.contains("www.") && url1.contains(".com")) {
                            webView.loadUrl("https://" + url1);
                        } else if (url1.contains(".com")) {
                            webView.loadUrl("https://www." + url1);
                        } else {
                            webView.loadUrl("https://www.google.com/search?q=" + url1);
                        }

                    }
                }
        );


    }

    void getvideofromurl(String url) {


        progressl.setTitle("Loading");
        progressl.setMessage("Fetching Videos ...please wait");
        progressl.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progressl.show();

        final ApiInterface apiservice = ApiClient.getClient().create(ApiInterface.class);


        Call<Example> call = apiservice.getvideo(url, "json");
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {

                if (response.body() != null) {
                    example = response.body();
                    if (example.getUrls() != null) {

                        showalertfordownload(example);
                    } else {
                        Toast.makeText(getActivity(), "No Video Found", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(getActivity(), "No Video Found", Toast.LENGTH_SHORT).show();
                }

                progressl.dismiss();


            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

                Log.d("failed", t.getMessage());
                Toast.makeText(getActivity(), "No Video Found", Toast.LENGTH_SHORT).show();
                progressl.dismiss();
            }


        });


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


    void showalertfordownload(final Example example) {

        if (example != null)

        {
            progressDialog2.setTitle("Loading");
            progressDialog2.setMessage("Almost there ...");
            progressDialog2.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progressDialog2.show();


            final String title = example.getTitle();

            final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

            alert.setCancelable(true);
            alert.setTitle(title);
            alert.setMessage("Choose quality");

            //Set an EditText view to get user input


            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);

            final Spinner spinner = new Spinner(getActivity());
            alert.setView(spinner);
            spinner.setPadding(10, 10, 10, 10);

            int size = example.getUrls().size();
            for (int i = 0; i < size; i++) {
                String add = example.getUrls().get(i).getLabel();
                arrayAdapter.add(add);
            }


            spinner.setAdapter(arrayAdapter);


            alert.setPositiveButton("Download", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    int position = spinner.getSelectedItemPosition();
                    if (position < 0) {
                        position = 0;
                    }
                    String url = example.getUrls().get(position).getId();

                    String d = example.getUrls().get(position).getLabel();
                    String extension = d.substring(d.length() - 3);
                    download(url, title, extension);


                }

            });
            alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            });
            progressDialog2.dismiss();

            alert.show();


        } else {
            Toast.makeText(getActivity(), "Internal  Error", Toast.LENGTH_SHORT).show();

        }

    }

    void download(String url, String title, String ext) {

        if (title.length() >= 50) {
            title = title.substring(0, Math.min(title.length(), 45));
        }

        title = title + "." + ext;
        Download_Uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
        request.allowScanningByMediaScanner();
        // request.setMimeType("video/" + ext);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle(title);
        request.setDescription("Downloading " + title);
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);


        long refid = downloadManager.enqueue(request);
        Toast.makeText(getActivity(), "Downloading " + ext + " file", Toast.LENGTH_SHORT).show();


    }
}



