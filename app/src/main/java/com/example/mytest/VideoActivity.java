package com.example.mytest;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class VideoActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private static final String TAG = "myLogs";

    private SurfaceHolder holder;
    private MyMedia myMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        String name = getIntent().getStringExtra("name");

        myMedia = new MyMedia(this, name);
        SurfaceView surface = (SurfaceView)findViewById(R.id.surfaceView);
        surface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myMedia.getMediaController().show();
            }
        });
        holder = surface.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        myMedia.OnStart();

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        myMedia.getMediaPlayer().setDisplay(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        myMedia.pause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        myMedia.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myMedia.OnDestroy();
    }
}
