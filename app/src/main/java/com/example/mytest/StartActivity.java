package com.example.mytest;

import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class StartActivity extends AppCompatActivity {

    private int page;

    private static final String TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_1);

        page = 1;

        paged();

    }

    View.OnClickListener ocBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            paged();
        }
    };

    View.OnClickListener ocBtnEnd = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FileManager fm = new FileManager(getApplicationContext());
            fm.addAkkaunt("user");
            if (!fm.getAkkaunt().isEmpty()) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }else{
                Log.d(TAG, "not akkaunt : ");
            }
        }
    };

    private void paged(){

        Button btn = null;
        Button btn1 = null;
        TextView textView = null;
        TextView textView1 = null;
        switch (getPage()){
            case 1:
                btn = (Button) findViewById(R.id.btnuz);
                btn.setOnClickListener(ocBtn);

                btn1 = (Button) findViewById(R.id.btnru);
                btn1.setOnClickListener(ocBtn);

                break;
            case 2:
                setContentView(R.layout.layout_2);
                btn = (Button) findViewById(R.id.btnofert);
                btn.setOnClickListener(ocBtn);
                break;
            case 3:
                setContentView(R.layout.layout_3);
                btn = (Button) findViewById(R.id.btndale1);
                btn.setOnClickListener(ocBtn);
                textView = (TextView) findViewById(R.id.textView4);
                textView.setText(R.string.layout_3text21);
                break;
            case 4:
                btn = (Button) findViewById(R.id.btndale1);
                btn.setOnClickListener(ocBtn);
                textView = (TextView) findViewById(R.id.textView4);
                textView.setText(R.string.layout_3text22);
                break;
            case 5:
                btn = (Button) findViewById(R.id.btndale1);
                btn.setOnClickListener(ocBtn);
                textView = (TextView) findViewById(R.id.textView4);
                textView.setText(R.string.layout_3text23);
                textView1 = (TextView) findViewById(R.id.textView3);
                textView.setText(R.string.layout_3text12);
                break;
            case 6:
                btn = (Button) findViewById(R.id.btndale1);
                btn.setOnClickListener(ocBtn);
                textView = (TextView) findViewById(R.id.textView4);
                textView.setText(R.string.layout_3text24);
                textView1 = (TextView) findViewById(R.id.textView3);
                textView.setText(R.string.layout_3text13);
                break;
            case 7:
                setContentView(R.layout.layout_4);
                btn = (Button) findViewById(R.id.btndale2);
                btn.setOnClickListener(ocBtn);
                break;
            case 8:
                setContentView(R.layout.layout_5);
                btn = (Button) findViewById(R.id.btndale3);
                btn.setOnClickListener(ocBtn);
                break;
            case 9:
                setContentView(R.layout.layout_6);
                btn = (Button) findViewById(R.id.btntas);
                btn.setOnClickListener(ocBtn);
                break;
            case 10:
                setContentView(R.layout.layout_7);
                TextView textView_pod = (TextView) findViewById(R.id.textView24);
                textView_pod.setPaintFlags(textView_pod.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                btn = (Button) findViewById(R.id.btntas2);
                btn.setOnClickListener(ocBtn);
                break;
            case 11:
                setContentView(R.layout.layout_8);
                btn = (Button) findViewById(R.id.btndaleend);
                btn.setOnClickListener(ocBtnEnd);
                break;
        }
        page++;
    }

    public int getPage() {
        return page;
    }
}
