package com.example.mytest;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.MediaController;
import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MyMedia implements MediaPlayer.OnPreparedListener, MediaController.MediaPlayerControl{
    private static final String TAG = "myLogs";

    private Context context;
    private String name;
    private MediaPlayer mediaPlayer;
    private VideoController videoController;


    public MyMedia(final Context context, String value) {
        this.context = context;
        this.name = value;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        videoController = new VideoController(context, this);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (videoController.isShowing()){
                    videoController.hide();
                }
                mediaPlayer.reset();
                ((Activity) context).finish();
            }
        });
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setsetPlaybackParams (PlaybackParams params){
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.setPlaybackParams(params);
        }
    }

    public void Stop(){
        if (videoController.isShowing()){
            videoController.hide();
        }
        if (isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }

    public void OnStart(){

        FileInputStream fis = null;
        try {
            try{
                File file = new File(name);
                fis = new FileInputStream(file);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(fis.getFD());
                mediaPlayer.prepare();

            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        Log.d(TAG, "IOException e1 : " + e.getMessage());
                    }
                }
            }
        }catch (IOException e){
            Log.d(TAG, "IOException e2 : " + e.getMessage());
        }

        mediaPlayer.start();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        videoController.setMediaPlayer(this);
        videoController.setAnchorView((SurfaceView) ((Activity) context).findViewById(R.id.surfaceView));
        videoController.setEnabled(true);
        videoController.show();
    }

    @Override
    public void start() {
        mediaPlayer.start();
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        mediaPlayer.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return mediaPlayer.getAudioSessionId();
    }

    public void OnDestroy(){
        Stop();
        mediaPlayer.release();
    }

    public MediaPlayer getMediaPlayer(){
        return mediaPlayer;
    }

    public VideoController getMediaController(){
        return videoController;
    }
}
