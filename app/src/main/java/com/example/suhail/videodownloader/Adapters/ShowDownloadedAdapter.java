package com.example.suhail.videodownloader.Adapters;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suhail.videodownloader.Model.DownloadedVideos;
import com.example.suhail.videodownloader.Model.Urls;
import com.example.suhail.videodownloader.R;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Suhail on 4/7/2018.
 */

public class ShowDownloadedAdapter extends RecyclerView.Adapter<ShowDownloadedAdapter.ViewHolder> {

    Uri path;
    ArrayList<DownloadedVideos> downloadedVideos = new ArrayList<>();
    Context context;
    Activity activity;

    public ShowDownloadedAdapter(ArrayList<DownloadedVideos> downloadedVideos, Context context, Activity activity) {
        this.downloadedVideos = downloadedVideos;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public ShowDownloadedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_show_downloaded, parent, false);


        return new ShowDownloadedAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShowDownloadedAdapter.ViewHolder holder, final int position) {

        Toast.makeText(context, downloadedVideos.get(position).getName(), Toast.LENGTH_SHORT).show();

        holder.title.setText(downloadedVideos.get(position).getName());

        final File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+ File.separator +
                downloadedVideos.get(position).getName());

        //path = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+File.separator+downloadedVideos.get(position).getName())
        holder.open.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Log.i("Opening Video",
                                String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS))+File.separator+downloadedVideos.get(position).getName());
                        Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                        pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        pdfOpenintent.setDataAndType(path, "video/mp4");


                    }
                }
        );


    }

    @Override
    public int getItemCount() {
        return downloadedVideos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        Button delete, open;

        public ViewHolder(View itemView) {

            super(itemView);

            delete = itemView.findViewById(R.id.delete_downloaded_vid);
            title = itemView.findViewById(R.id.downloaded_vid_title);
            open = itemView.findViewById(R.id.open_downloaded_vid);


        }
    }
}
