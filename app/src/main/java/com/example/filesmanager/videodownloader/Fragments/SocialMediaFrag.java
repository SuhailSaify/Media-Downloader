package com.example.filesmanager.videodownloader.Fragments;

import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.filesmanager.videodownloader.MainActivity;
import com.example.filesmanager.videodownloader.NoConnectionActivity;
import com.example.filesmanager.videodownloader.R;

import java.util.zip.Inflater;


public class SocialMediaFrag extends Fragment {


    ProgressDialog progressl;
    boolean backtomain = true;
    LinearLayout webviewLayout;
    ScrollView optionLayout;
    CardView fb, twitter, others, insta, fb1;
    TextView textView;
    ImageView back;
    int visible = View.VISIBLE;
    int invisible = View.GONE;

    String urlinsta = "https://downloadgram.com/"
            //        "https://www.insloader.com/"
            ;

    String urlfb1 = "https://www.fbdown.net/";

    String urlfb =
            "https://fbcopy.com/"
            //"https://www.fbdown.net/"

            //"https://en.savefrom.net/"
            ;
    String urltwttr =
            "https://twdown.net/"

            //"https://en.savefrom.net/"
            ;
    String urlothers =


            "https://en.savefrom.net/";
    WebView webView;
    ProgressBar progressBar;
    LinearLayout layout;

    public SocialMediaFrag() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Boolean isConnected;

        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {

            progressBar = getActivity().findViewById(R.id.pg_bar_social_media);
            progressl = new ProgressDialog(getActivity());
            back = getActivity().findViewById(R.id.goback_socialmedia);
            webviewLayout = getActivity().findViewById(R.id.layout_webview);
            optionLayout = getActivity().findViewById(R.id.downloads_options);
            fb = getActivity().findViewById(R.id.cardfb);
            fb1 = getActivity().findViewById(R.id.cardfb1);
            twitter = getActivity().findViewById(R.id.cardtwttr);
            others = getActivity().findViewById(R.id.cardother);
            insta = getActivity().findViewById(R.id.insta);
            webView = getActivity().findViewById(R.id.web_view_socailmedia);
            setvisisblity(invisible, visible);
            backtomain = true;

            listenerOne(view);


        } else {
            startActivity(new Intent(getActivity(), NoConnectionActivity.class));
        }

        Listener();
    }

    public void listenerOne(final View view) {
        insta.setOnClickListener(
                new
                        View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setvisisblity(visible, invisible);
                                setupview(view, urlinsta, true);

                            }
                        }
        );

        fb.setOnClickListener(
                new
                        View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setvisisblity(visible, invisible);
                                setupview(view, urlfb, true);

                            }
                        }
        );
        fb1.setOnClickListener(
                new
                        View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setvisisblity(visible, invisible);
                                setupview(view, urlfb1, true);

                            }
                        }
        );
        twitter.setOnClickListener(
                new
                        View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setvisisblity(visible, invisible);
                                setupview(view, urltwttr, true);

                            }
                        }
        );
        others.setOnClickListener(
                new
                        View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setvisisblity(visible, invisible);
                                setupview(view, urlothers, true);

                            }
                        }
        );
    }

    public void setupview(final View view, final String url, boolean b) {
        setvisisblity(visible, invisible);

        backtomain = false;



     /*   view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                //r will be populated with the coordinates of your view that area still visible.
                view.getWindowVisibleDisplayFrame(r);

                int heightDiff = view.getRootView().getHeight() - (r.bottom - r.top);
                if (heightDiff > 500) { // if more than 100 pixels, its probably a keyboard...

                } else {

                }
            }
        });
*/

        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        float h = dpHeight - 474;
        int w1 = (int) (dpWidth * displayMetrics.density);
        int h1 = (int) (dpHeight * displayMetrics.density);
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (h * scale + 0.5f);

        if (url.equals(urlothers)) {

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    //new ViewGroup.LayoutParams(
                    //(int)dpWidth ,
                    //(int)dpWidth)
                    w1, h1
            );
            webView.setLayoutParams(layoutParams);
        } else {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    //new ViewGroup.LayoutParams(
                    //(int)dpWidth ,
                    //(int)dpWidth)
                    w1, h1);
            webView.setLayoutParams(layoutParams);
        }


        Log.d("size", String.valueOf(dpHeight) + "--" + String.valueOf(dpWidth)
        );

        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(getContext().CLIPBOARD_SERVICE);
        //clipboard.setText("Text to copy");
        if (clipboard.getText() != null) {
            String testur = clipboard.getText().toString();

            if (testur.contains("youtube") || testur.contains("youtu.be")) {


                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                alert.setCancelable(false);
                alert.setTitle("Cannot Download from Youtube");
                alert.setMessage("try another url");

// Set an EditText view to get user input
            /*  final EditText input = new EditText(getActivity());
              alert.setView(input);
              input.setPadding(10, 10, 10, 10);
*/

                alert.setPositiveButton("Go Back", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {


                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new MainFrag()).commit();

                    }

                });


                alert.show();


            }
        }




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
            //  setDesktopMode(webView,true);

            if (!b) {


                webView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return (event.getAction() == MotionEvent.ACTION_MOVE);
                    }
                });
            }

            webView.setWebChromeClient(new WebChromeClient() {


                public void onProgressChanged(WebView view, int progress) {


                    progressl.setTitle("Loading");
                    progressl.setMessage("Wait while loading...");
                    progressl.setCancelable(false); // disable dismiss by tapping outside of the dialog
                    progressl.show();
// To dismiss the dialog


                    if ((!webView.getUrl().equals(url))
                            && (!webView.getUrl().equals("https://www.fbdown.net/download.php"))
                            && (!webView.getUrl().equals("https://twdown.net/download.php"))
                            ) {
                        webView.loadUrl(url);
                    }

                    if (progress > 70) {
                        progressl.dismiss();
                    }

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


                            if (url.contains("youtube") || url.contains("youtu.be")) {

                                Toast.makeText(getContext(), "called", Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                                alert.setTitle("Cannot Download from Youtube");
                                alert.setMessage("try another url");

// Set an EditText view to get user input
            /*  final EditText input = new EditText(getActivity());
              alert.setView(input);
              input.setPadding(10, 10, 10, 10);
*/

                                alert.setPositiveButton("Go Back", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {


                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new MainFrag()).commit();


                                    }

                                });


                                alert.show();


                            }

                            activity.download(url);
                            Toast.makeText(getContext(), "Downloading", Toast.LENGTH_SHORT).show();

                        }
                    }
            );

        } else {
            Toast.makeText(getActivity(), "SOME ERROR ACCURED ", Toast.LENGTH_SHORT).show();

        }


    }


    private void Listener() {


        if (back != null)

        {

            back.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            progressBar.setProgress(0);

                            if (backtomain) {
                                FragmentManager fragmentManager2 = getFragmentManager();
                                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                                MainFrag fragment2 = new MainFrag();
                                fragmentTransaction2.replace(R.id.main_container, fragment2).commit();
                            } else {
                                setvisisblity(invisible, visible);
                                backtomain = true;
                            }
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


    public void setDesktopMode(WebView webView, boolean enabled) {
        String newUserAgent = webView.getSettings().getUserAgentString();
        if (enabled) {
            try {
                String ua = webView.getSettings().getUserAgentString();
                String androidOSString = webView.getSettings().getUserAgentString().substring(ua.indexOf("("), ua.indexOf(")") + 1);
                newUserAgent = webView.getSettings().getUserAgentString().replace(androidOSString, "(X11; Linux x86_64)");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            newUserAgent = null;
        }

        webView.getSettings().setUserAgentString(newUserAgent);
        webView.getSettings().setUseWideViewPort(enabled);
        webView.getSettings().setLoadWithOverviewMode(enabled);
        webView.reload();
    }


    public void setvisisblity(int view1, int view2) {
        webviewLayout.setVisibility(view1);
        optionLayout.setVisibility(view2);
    }

}
