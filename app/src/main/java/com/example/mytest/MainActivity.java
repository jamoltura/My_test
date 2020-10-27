package com.example.mytest;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    final String readExternalStorage = Manifest.permission.READ_EXTERNAL_STORAGE;
    final String writeExternalStorage = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    final int MY_READE = 1;
    final int MY_WRITES = 2;

    private HashMap<Long, String> listIdDownload;

    private static final String TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(!getPermission(readExternalStorage)){
            RequestPermission(readExternalStorage, MY_READE);
        }

        listIdDownload = new HashMap<Long, String>();

        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        filter.addAction(DownloadManager.ACTION_VIEW_DOWNLOADS);

        registerReceiver(DownloadReceiver, filter);

        FileManager fm = new FileManager(this);

       // fm.delbase();

       // fm = new FileManager(this);

        String akkauntName = fm.getAkkaunt();

        if (akkauntName.isEmpty()) {
            if (fm.getCountBase() == 0) {
                addBase();
            }
            Intent intent = new Intent(getBaseContext(), StartActivity.class);
            startActivity(intent);
            finish();
        }else{
            layoutActivity();
        }
    }

    private void layoutActivity(){
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.button4);
        btn.setOnClickListener(btnMain1);

        Button btn2 = (Button) findViewById(R.id.button5);
        btn2.setOnClickListener(btnMain2);
    }

    private void layoutVideo(){
        setContentView(R.layout.layout_video);

        ImageButton imgbtn = (ImageButton) findViewById(R.id.imgBtn1);
        imgbtn.setOnClickListener(imgbtn1);

        FloatingActionButton fl = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fl.setOnClickListener(fltbtn);
    }

    private void layoutTest(){
        setContentView(R.layout.layout_test);

        ImageButton imgbtn = (ImageButton) findViewById(R.id.imgBtn2);
        imgbtn.setOnClickListener(imgbtn2);
    }

    private View.OnClickListener btnMain1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            layoutVideo();

            ListView listView = (ListView) findViewById(R.id.listmund);
            listView.setAdapter(new AdapterMund(getMainActivity()));
        }
    };

    private View.OnClickListener btnMain2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            layoutTest();
        }
    };

    private View.OnClickListener imgbtn1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            layoutActivity();
        }
    };

    private View.OnClickListener imgbtn2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            layoutActivity();
        }
    };

    private View.OnClickListener fltbtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getBaseContext(), VideoActivity.class);
            FileManager fl = new FileManager(getApplicationContext());
            String name = "";
            int count = fl.getCountBase();
            for (int i = 0; i < count; i++){
                try {
                    JSONObject obj = fl.getItemBase(i);
                    String str = obj.getString("pathVideo");

                    if (!str.isEmpty()){
                        name = str;
                        break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            if (!name.isEmpty()) {
                intent.putExtra("name", name);
                startActivity(intent);
            }
        }
    };

    private MainActivity getMainActivity(){
        return this;
    }

    private void addBase(){

        String servis = "https://getfile.dokpub.com/yandex/get/";

        FileManager fm = new FileManager(this);

        String name = "Изменил свое мнение";
        String link = servis + "https://yadi.sk/i/i2ulISlIlWZijw";
        String nameVideo = "Изменил свое мнение про самоучек в программировании.mp4";
        String sizeVideo = "22mb";
        fm.addMundarija(name, link, nameVideo, sizeVideo);

        name = "Что такое framework";
        link = servis + "https://yadi.sk/i/1uIPe5-GRDuEJA";
        nameVideo = "Что такое framework Объяснение для новичков.mp4";
        sizeVideo = "15mb";
        fm.addMundarija(name, link, nameVideo, sizeVideo);

        name = "Kotlin в Foxminded";
        link = servis + "https://yadi.sk/i/PvqSw3yjP0khGQ";
        nameVideo = "Kotlin в Foxminded_ обновление программы курса Android.mp4";
        sizeVideo = "15mb";
        fm.addMundarija(name, link, nameVideo, sizeVideo);

        name = "Ularni g`ordan kim";
        link = servis + "https://yadi.sk/i/uQzz3qdRqbSiLA";
        nameVideo = "Ularni g`ordan kim chiqaradi.mp4";
        sizeVideo = "20mb";
        fm.addMundarija(name, link, nameVideo, sizeVideo);

        name = "Zokir Ochildiyev";
        link = servis + "https://yadi.sk/i/L6PWPY_6iNaTcw";
        nameVideo = "Zokir Ochildiyev.mp4";
        sizeVideo = "6mb";
        fm.addMundarija(name, link, nameVideo, sizeVideo);

        name = "Million jamoasi";
        link = servis + "https://yadi.sk/i/UScbFmeXlcOtuQ";
        nameVideo = "Million jamoasi.mp4";
        sizeVideo = "21mb";
        fm.addMundarija(name, link, nameVideo, sizeVideo);

        name = "Исследуем float";
        link = servis + "https://yadi.sk/i/xEaeS_F1xfMhVA";
        nameVideo = "Исследуем float.mp4";
        sizeVideo = "19mb";
        fm.addMundarija(name, link, nameVideo, sizeVideo);

        name = "Как сделать ANDROID";
        link = servis + "https://yadi.sk/i/ntabEFhJxzZ60g";
        nameVideo = "Как сделать ANDROID.mp4";
        sizeVideo = "23mb";
        fm.addMundarija(name, link, nameVideo, sizeVideo);

        name = "Ошибок всех";
        link = servis + "https://yadi.sk/i/B7NreJ7RrXtsCw";
        nameVideo = "ошибок всех.mp4";
        sizeVideo = "27mb";
        fm.addMundarija(name, link, nameVideo, sizeVideo);
    }

    private final BroadcastReceiver DownloadReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())){

                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(id);
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                Cursor c = dm.query(query);

                if (c.moveToFirst()){

                    int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);

                    if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {

                        boolean isBool = false;

                        for (Map.Entry<Long, String> item : getListIdDownload().entrySet()) {
                            if (item.getKey() == id) {

                                try {
                                    InputStream streamfile = new FileInputStream(dm.openDownloadedFile(id).getFileDescriptor());

                                    FileManager fm = new FileManager(context);
                                    File file = new File(fm.getPathMyVideo(), item.getValue());

                                    copy(streamfile, file);

                                    isBool = true;

                                    dm.remove(id);

                                    streamfile.close();

                                    Toast.makeText(context, "Загрузка завершена", Toast.LENGTH_LONG).show();
                                    ListView listView = (ListView) findViewById(R.id.listmund);
                                    listView.setAdapter(null);
                                    listView.setAdapter(new AdapterMund(getMainActivity()));

                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if (isBool){
                            getListIdDownload().remove(id);
                        }
                    } else if (DownloadManager.STATUS_FAILED == c.getInt(columnIndex)) {
                        Toast.makeText(context, "Ошибка при загрузке !!!", Toast.LENGTH_LONG).show();
                    } else if (DownloadManager.STATUS_RUNNING == c.getInt(columnIndex)) {
                        Toast.makeText(context, "Загрузка началась", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    };

    private void copy(InputStream in, File target) throws IOException {

        OutputStream out = new FileOutputStream(target);

        byte[] buf = new byte[1024];
        int len;

        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        out.flush();
        out.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(DownloadReceiver);
    }

    private Boolean getPermission(String value){
        return (ContextCompat.checkSelfPermission(this, value) == PackageManager.PERMISSION_GRANTED);
    }

    private void RequestPermission(String value, int result){
        ActivityCompat.requestPermissions(this, new String[]{value}, result);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_READE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (!getPermission(writeExternalStorage)){
                    RequestPermission(writeExternalStorage, MY_WRITES);
                }
            }
        }
    }

    public HashMap<Long, String> getListIdDownload() {
        return listIdDownload;
    }

    public void setListIdDownload(HashMap<Long, String> listIdDownload) {
        this.listIdDownload = listIdDownload;
    }
}
