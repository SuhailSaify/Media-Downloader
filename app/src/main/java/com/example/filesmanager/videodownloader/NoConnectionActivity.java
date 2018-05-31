package com.example.filesmanager.videodownloader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class NoConnectionActivity extends AppCompatActivity {
    ImageView imageView;
    Button retry;
    boolean isConnected = false;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_connection);
        context = NoConnectionActivity.this;




       /* Glide.with(context)
                .load(getResources().getDrawable(R.drawable.preview))
                .into(imageView);
        */


        retry = findViewById(R.id.retrynet);
        retry.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ConnectivityManager cm =
                                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

                        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                        isConnected = activeNetwork != null &&
                                activeNetwork.isConnectedOrConnecting();

                        if (isConnected) {
                            startActivity(new Intent(NoConnectionActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(context, "NOT CONNECTED", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );


    }
}
