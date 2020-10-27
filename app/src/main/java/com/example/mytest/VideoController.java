package com.example.mytest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.media.PlaybackParams;
import android.os.Build;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import androidx.annotation.RequiresApi;

public class VideoController extends MediaController {

    private Context context;
    private MyMedia myMedia;
    private static final String TAG = "myLogs";

    public VideoController(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public VideoController(Context context, boolean useFastForward) {
        super(context, useFastForward);
        this.context = context;
    }

    public VideoController(Context context, MyMedia myMedia) {
        super(context);
        this.myMedia = myMedia;
        this.context = context;
    }

    @Override
    public void setAnchorView(View view) {
        super.setAnchorView(view);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, (float) 3.5);

        RelativeLayout relativeLayout1 = new RelativeLayout(context);
        relativeLayout1.setLayoutParams(params);

        params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, (float) 6.5);

        RelativeLayout relativeLayout2 = new RelativeLayout(context);
        relativeLayout2.setLayoutParams(params);

        params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        LinearLayout l = new LinearLayout(context);
        l.setOrientation(LinearLayout.HORIZONTAL);
        l.setLayoutParams(params);

        params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,1);

        Button midle = new Button(context);
        midle.setBackgroundColor(Color.TRANSPARENT);
        midle.setTextColor(Color.WHITE);
        midle.setText("0,5X");
        midle.setLayoutParams(params);

        midle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                myMedia.setsetPlaybackParams(new PlaybackParams().setSpeed((float) 0.5));
            }
        });

        Button norm = new Button(context);
        norm.setBackgroundColor(Color.TRANSPARENT);
        norm.setTextColor(Color.WHITE);
        norm.setText("X");
        norm.setLayoutParams(params);

        norm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                myMedia.setsetPlaybackParams(new PlaybackParams().setSpeed((float) 1));
            }
        });

        Button fast = new Button(context);
        fast.setBackgroundColor(Color.TRANSPARENT);
        fast.setTextColor(Color.WHITE);
        fast.setText("1,5X");
        fast.setLayoutParams(params);

        fast.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                myMedia.setsetPlaybackParams(new PlaybackParams().setSpeed((float) 1.5));
            }
        });

        Button highfast = new Button(context);
        highfast.setBackgroundColor(Color.TRANSPARENT);
        highfast.setTextColor(Color.WHITE);
        highfast.setText("2X");
        highfast.setLayoutParams(params);

        highfast.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                myMedia.setsetPlaybackParams(new PlaybackParams().setSpeed((float) 2));
            }
        });

        l.addView(midle);
        l.addView(norm);
        l.addView(fast);
        l.addView(highfast);

        relativeLayout2.addView(l);

        params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(params);

        linearLayout.addView(relativeLayout1);
        linearLayout.addView(relativeLayout2);

        addView(linearLayout, params);
    }
}
