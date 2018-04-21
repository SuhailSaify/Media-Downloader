package com.example.suhail.videodownloader.Adapters;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.downloader.Status;
import com.example.suhail.videodownloader.Model.DownloadedVideos;
import com.example.suhail.videodownloader.Model.Urls;
import com.example.suhail.videodownloader.R;
import com.example.suhail.videodownloader.Services.DownloadService;
import com.example.suhail.videodownloader.Utils.DownloadedVidShared;
import com.example.suhail.videodownloader.Utils.ShredPref;
import com.example.suhail.videodownloader.Utils.Utils;



import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Suhail on 4/4/2018.
 */

public class DownloadListAdapter extends RecyclerView.Adapter<DownloadListAdapter.ViewHolder> {

    //DownloadedVidShared downloadedVidShared;
    boolean isnew = true;
    ShredPref shredPref;
    DownloadService downloadService;
    Boolean isbound = false;
    ServiceConnection serviceConnection;
    Activity activity;
    DownloadedVidShared downloadedVidShared;
    ArrayList<DownloadedVideos> downloadedVideos = new ArrayList<>();
    String dirPath;
    String name;
    ArrayList<Urls> data = new ArrayList<>();
    Context context;
    String i;


    public DownloadListAdapter(ArrayList<Urls> data, Context context, Activity activity, String dirPath) {

        this.data = data;
        this.activity = activity;
        this.context = context;
        this.dirPath = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
        downloadedVidShared = new DownloadedVidShared(context, activity);
        shredPref = new ShredPref(context);
        addservice();


    }

    private void addservice() {

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

                DownloadService.LocalBinder binder = (DownloadService.LocalBinder) iBinder;
                downloadService = binder.getService();
                isbound = true;

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                isbound = false;
            }
        };

        Intent intent = new Intent(context, DownloadService.class);
        context.startService(intent);
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_download, parent, false);
        return new DownloadListAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // Toast.makeText(context, "caleed", Toast.LENGTH_SHORT).show();


        holder.progressBarOne.setProgress(shredPref.getprogress(position));
        if (shredPref.getprogress(position) == 100) {
            holder.buttonOne.setText("COMPLETE");
            holder.buttonOne.setEnabled(false);
        }


        name = URLUtil.guessFileName(data.get(position).getUrl(), null, null);
        final String url = data.get(position).getUrl();
        BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Get extra data included in the Intent

                int pos;
                pos = intent.getIntExtra("pos", 0);
                if (pos == position) {
                    int p = intent.getIntExtra("Status", 0);
                    holder.progressBarOne.setProgress(p);
                    shredPref.put(p, position);
                    Log.d("d4", String.valueOf(p));


                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "DOWNLLOAD");
                    mBuilder.setContentTitle("Download")
                            .setContentText("Download in progress")
                            .setSmallIcon(R.drawable.ic_download)
                            .setAutoCancel(false)
                            .setPriority(NotificationCompat.PRIORITY_LOW);

// Issue the initial notification with zero progress
                    int PROGRESS_MAX = 100;
                    int PROGRESS_CURRENT = 0;
                    mBuilder.setProgress(PROGRESS_MAX, p, false);
                    notificationManager.notify(position, mBuilder.build());

// Do the job here that tracks the progress.
// Usually, this should be in a worker thread
// To show progress, update PROGRESS_CURRENT and update the notification with:
// mBuilder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
// notificationManager.notify(notificationId, mBuilder.build());

// When done, update the notification one more time to remove the progress bar


                    if (p == 100)

                    {
                        mBuilder.setContentText("Download complete")
                                .setProgress(0, 0, false);
                        notificationManager.notify(position, mBuilder.build());


                        holder.buttonOne.setText("COMPLETE");
                        holder.buttonOne.setEnabled(false);
                        data.get(position).setIsdownloaded(true);
                        downloadedVideos.add(new DownloadedVideos(url, dirPath, name));
                        downloadedVidShared.savevid(downloadedVideos);
                    }

                }
            }
        };


        LocalBroadcastManager.getInstance(activity).registerReceiver(
                mMessageReceiver, new IntentFilter("SendProgress"));


        name = URLUtil.guessFileName(data.get(position).getUrl(), null, null);

        holder.filename.setText(name);


        //  holder.progressBarOne.setProgress(progrss);

        holder.buttonremove.setOnClickListener(
                new View.OnClickListener()

                {
                    @Override
                    public void onClick(View view) {

                        data.remove(holder.getAdapterPosition());
                        notifyDataSetChanged();
                        downloadedVidShared.saveURL(data);

                        // downloadService.pause(id.get(holder.getAdapterPosition()));

                    }
                }
        );


        ArrayList<Urls> ar = downloadedVidShared.geturl();

        if (ar.contains(data.get(position)))

        {
            Toast.makeText(activity, "already downaloaded", Toast.LENGTH_SHORT).show();
        }

       /*BroadcastReceiver mMessageReceiver2 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Get extra data included in the Intent

                int i = intent.getIntExtra("DownloadID", -1);
                int posi = intent.getIntExtra(" posi", -1);

                if (posi >= 0) {

                    id.add(posi, i);
                }
              /*  if (intent.getIntExtra(" posi", -1) == holder.getAdapterPosition()) {
                    Toast.makeText(context, "set", Toast.LENGTH_SHORT).show();
                    int i = intent.getIntExtra("DownloadID", -1);
                    id.add(holder.getAdapterPosition(), i);
                    Log.d("idreciecd", String.valueOf(id));

            }

            }
        };

        LocalBroadcastManager.getInstance(activity).

                registerReceiver(
                        mMessageReceiver2, new IntentFilter("SendID"));*/
        //  startdownload(data.get(holder.getAdapterPosition()).getUrl(), dirPath, holder.getAdapterPosition());


        if (downloadService != null && isnew && downloadService.checkforpauseresume(position).equals("UNKNOWN")) {


            downloadService.startdownload(data.get(position).getUrl(), data.get(position).getPath(), position, name);
            isnew = false;
            holder.buttonOne.setText("Pause");
            shredPref.putbutton("PAUSE", position);
            holder.buttonOne.setEnabled(true);

        }

        holder.buttonOne.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {


                 /*else if (holder.buttonOne.getText().equals("COMPLETE")) {

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(dirPath));
                    intent.setDataAndType(Uri.parse(dirPath), "video/mp4");
                    context.startActivity(intent);

                    Toast.makeText(context, "", Toast.LENGTH_SHORT).show();


                }*/
                {


                    String s = downloadService.checkforpauseresume(position);
                    holder.buttonOne.setText(s);
                    shredPref.putbutton(s, position);
                    holder.buttonOne.setEnabled(true);


                }


              /*  if(i!=null)
                {
                    switch (i) {
                        case "PAUSED":

                            downloadService.resume((holder.getAdapterPosition()));
                            holder.buttonOne.setText("RESUME");
                            break;
                        case "RUNNING":
                        case "QUEUED":
                            downloadService.pause((holder.getAdapterPosition()));
                            holder.buttonOne.setText("PAUSE");
                            break;
                        default:
                            holder.buttonOne.setText("Start");
                            break;
                    }
                }

                BroadcastReceiver mMessageReceiver3 = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        // Get extra data included in the Intent

                        Toast.makeText(context, String.valueOf(intent.getIntExtra(" position", -1)), Toast.LENGTH_SHORT).show();

                        if (intent.getIntExtra(" position", -1) == holder.getAdapterPosition()) {
                             i = intent.getStringExtra("status");
                            Toast.makeText(context, "set"+i, Toast.LENGTH_SHORT).show();

                        }

                    }
                };

                LocalBroadcastManager.getInstance(activity).registerReceiver(
                        mMessageReceiver3, new IntentFilter("sendStatus"));*/


                //   progrss = downloadService.getprogress();

                /*     String url = data.get(position).getUrl();
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.setDescription("Some descrition");
                request.setTitle("Some title");
// in order for this if to run, you must use the android 3.2 to compile your app
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name);

// get download service and enqueue file
                DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                if (manager != null) {
                    manager.enqueue(request);
                }
              */

          /*     if (Status.RUNNING == PRDownloader.getStatus(data.get(position).getId())) {
                    PRDownloader.pause(data.get(position).getId());
                    return;
                }

                holder.buttonOne.setEnabled(false);

                holder.progressBarOne.setIndeterminate(true);
                holder.progressBarOne.getIndeterminateDrawable().setColorFilter(
                        Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);

                if (Status.PAUSED == PRDownloader.getStatus(data.get(position).getId())) {
                    PRDownloader.resume(data.get(position).getId());
                    return;
                }

                data.get(position).setId(PRDownloader.download(data.get(position).getUrl(), dirPath, name)
                        .build()
                        .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                            @Override
                            public void onStartOrResume() {
                                holder.progressBarOne.setIndeterminate(false);
                                holder.buttonOne.setEnabled(true);
                                holder.buttonOne.setText(R.string.pause);

                                holder.buttonCancelOne.setEnabled(true);
                            }
                        })
                        .setOnPauseListener(new OnPauseListener() {
                            @Override
                            public void onPause() {
                                holder.buttonOne.setText(R.string.resume);
                            }
                        })
                        .setOnCancelListener(new OnCancelListener() {
                            @Override
                            public void onCancel() {
                                holder.buttonOne.setText(R.string.start);
                                holder.buttonCancelOne.setEnabled(false);
                                holder.progressBarOne.setProgress(0);

                                holder.textViewProgressOne.setText("");
                                data.get(position).setId(0);
                                holder.progressBarOne.setIndeterminate(false);
                            }
                        })
                        .setOnProgressListener(new OnProgressListener() {
                            @Override
                            public void onProgress(Progress progress) {
                                long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                                holder.progressBarOne.setProgress((int) progressPercent);

                                holder.textViewProgressOne.setText(Utils.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
                                holder.progressBarOne.setIndeterminate(false);
                            }
                        })
                        .start(new OnDownloadListener() {
                            @Override
                            public void onDownloadComplete() {
                                holder.buttonOne.setEnabled(false);
                                holder.buttonCancelOne.setEnabled(false);
                                holder.buttonOne.setText(R.string.completed);

                                downloadedVideos.add(new DownloadedVideos(name, dirPath + File.separator + name, data.get(position).getUrl()));
                                DownloadedVidShared downloadedVidShared = new DownloadedVidShared(context, activity);
                                downloadedVidShared.savevid(downloadedVideos);
                            }

                            @Override
                            public void onError(Error error) {
                                holder.buttonOne.setText(R.string.start);
                                Toast.makeText(context, context.getString(R.string.some_error_occurred) + " " + "1", Toast.LENGTH_SHORT).show();
                                holder.textViewProgressOne.setText("");
                                holder.progressBarOne.setProgress(0);
                                data.get(position).setId(0);
                                holder.buttonCancelOne.setEnabled(false);
                                holder.progressBarOne.setIndeterminate(false);
                                holder.buttonOne.setEnabled(true);
                            }
                        }));


           */


            }
        });
        //  }
        holder.buttonCancelOne.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                PRDownloader.cancel(data.get(holder.getAdapterPosition()).getId());
            }
        });

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button buttonOne, buttonCancelOne, buttonremove;

        TextView textViewProgressOne, filename;

        ProgressBar progressBarOne;


        public ViewHolder(View itemView) {
            super(itemView);
            buttonremove = itemView.findViewById(R.id.buttonremove_download);
            buttonCancelOne = itemView.findViewById(R.id.buttonCancel_download);
            buttonOne = itemView.findViewById(R.id.buttonone_download);
            progressBarOne = itemView.findViewById(R.id.progressBar_download);
            textViewProgressOne = itemView.findViewById(R.id.textViewProgress_download);
            filename = itemView.findViewById(R.id.filename_download);
        }
    }
}




