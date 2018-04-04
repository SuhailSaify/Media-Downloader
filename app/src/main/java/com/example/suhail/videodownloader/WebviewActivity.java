package com.example.suhail.videodownloader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WebviewActivity extends AppCompatActivity {

    EditText searchbar_text;
    Button go_button;
    WebView webView;


    @Override
    public void onBackPressed() {
        startActivity(new Intent(WebviewActivity.this, MainActivity.class));

        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);


        searchbar_text = findViewById(R.id.search_bar);
        go_button = findViewById(R.id.gobutton);
        webView = findViewById(R.id.web);

        webView.setWebViewClient(new WebViewClient());
      // searchbar_text.setText(webView.getUrl());
        webView.loadUrl("https://www.google.com/");
        go_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (searchbar_text.getText() != null) {
                            //  webView.loadUrl(searchbar_text.getText().toString());
                        } else {
                            Toast.makeText(WebviewActivity.this, "Enter text to search", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

    }
}
