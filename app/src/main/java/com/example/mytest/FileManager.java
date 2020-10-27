package com.example.mytest;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class FileManager {

    private Context context;

    // Полный пут к папкам ////////////////
    private String pathDataBase;
    private String pathMyVideo;

    //  Свойства /////////////////////////
    private JSONArray listFileMyVideo;
    private JSONArray listMund;
    private SQLiteDatabase db;

    // имена атрибутов для Map
    final String PATH = "path";
    final String NAME = "name";

    private static final String TAG = "myLogs";

    public FileManager(Context context) {
        this.context = context;
        pathDataBase = _addDirectory(context.getFilesDir() + getDataBase());
        pathMyVideo = _addDirectory(context.getExternalFilesDir(null) + getMyVideo());
        createDataBase();
        try {
            init();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void init() throws JSONException {
        listFileMyVideo();
        try {
            initJSON();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String _addDirectory(String value) throws SecurityException{
        if (IsDirectory(value)) {
            return value;
        }
        return "";
    }

    private Boolean IsDirectory(String value) throws SecurityException{
        File theDirTemp = new File(value);
        if (!theDirTemp.exists()) {
            theDirTemp.mkdir();
        }
        return theDirTemp.exists();
    }

    /////////////////
    //  File MyVideo
    /////////////////

    private void listFileMyVideo() throws JSONException {

        listFileMyVideo = new JSONArray();

        int count = countFileMyVideo();

        for (int i = 0; count > i; i++) {

            JSONObject obj = new JSONObject();
            obj.put("name", get_FileName(getPathMyVideo(), i));
            obj.put("path", get_FilePath(getPathMyVideo(), i));

            listFileMyVideo.put(obj);
        }
    }

    private int countFileMyVideo(){
        return countFile(getPathMyVideo());
    }

    private int countVideo(){
        return listFileMyVideo.length();
    }

    /////////////////////////// private functions ///////////////////////////

    private String get_FilePath(String put, int ind) {

        File file = new File(put);
        File[] files = file.listFiles();

        try {
            if (files[ind].isFile()) {
                return files[ind].getAbsolutePath();
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception error : " + e.getMessage());
        }
        return null;
    }

    private String get_FileName(String put, int ind) {

        File file = new File(put);
        File[] files = file.listFiles();

        try {
            if (files[ind].isFile()) {
                return files[ind].getName();
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception error : " + e.getMessage());
        }
        return null;
    }

    private int countFile(String value){
        File file = new File(value);
        File[] files = file.listFiles();

        return files.length;
    }

    ////////////////
    // DataBase
    ///////////////

    private void createDataBase(){

        db = context.openOrCreateDatabase(getPathDataBase() + getDataBaseName(), MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS akkaunt (name TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS mundarija (name TEXT, link TEXT, nameVideo TEXT, sizeVideo TEXT)");
        //   db.execSQL("CREATE TABLE IF NOT EXISTS locat (name TEXT, latitude TEXT, longitude TEXT, date TEXT)");
    }

    public void addAkkaunt(String name){
        db.execSQL("INSERT INTO akkaunt VALUES ('"+name+"');");
    }

    public String getAkkaunt(){
        Cursor query = db.query("akkaunt", null, null, null, null,null,null);

        if(query.moveToFirst()) {
            do {
                int i = query.getColumnIndex("name");
                if (!query.getString(i).isEmpty()){
                    return query.getString(i);
                }
            } while (query.moveToNext());
        }

        return "";
    }

    public void addMundarija(String name, String link, String nameVideo, String sizeVideo){
        ContentValues c = new ContentValues();
        c.put("name", name);
        c.put("link", link);
        c.put("nameVideo", nameVideo);
        c.put("sizeVideo", sizeVideo);
        db.insert("mundarija", null, c);
        try {
            init();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONArray getAllnameMundarija() throws JSONException{

        Cursor query = db.query("mundarija", null, null, null, null,null,null);

        JSONArray ja = new JSONArray();

        if(query.moveToFirst()) {
            do {
                String name = query.getString(0);
                String link = query.getString(1);
                String nameVideo = query.getString(2);
                String sizeVideo = query.getString(3);
                String path = "";

                int count = countVideo();

                for (int i = 0; i < count; i++){

                    JSONObject obj = getVideo(i);
                    String tempName = obj.getString("name");

                    if (tempName.compareTo(nameVideo) == 0){
                        path = obj.getString("path");
                    }
                }

                JSONObject js = getMund(name, link, nameVideo, sizeVideo, path);

                ja.put(js);
            } while (query.moveToNext());
        }
        return ja;
    }

    private JSONObject getMund(String name, String link, String nameVideo, String sizeVideo, String path) throws JSONException {

        JSONObject js = new JSONObject();
        js.put("name", name);
        js.put("link", link);
        js.put("nameVideo", nameVideo);
        js.put("sizeVideo", sizeVideo);
        js.put("pathVideo", path);

        return js;
    }

    private JSONObject getVideo(int ind) throws JSONException {
        return listFileMyVideo.getJSONObject(ind);
    }

    public JSONObject getItemBase(int ind) throws JSONException {
        return getListMund().getJSONObject(ind);
    }

    public JSONObject getItemBase(String value) throws JSONException {
        int count = getCountBase();
        if (count > 0) {
            for (int i = 0; i < count; i++){
                JSONObject obj = getItemBase(i);
                String name = obj.getString("name");
                if (value.compareTo(name) == 0){
                    return obj;
                }
            }
        }
        return null;
    }

    public int getCountBase(){
        return getListMund().length();
    }

    public String getLinkMundarija(int ind) throws JSONException {
        return listMund.getJSONObject(ind).getString("link");
    }

    public String getnameVideoMundarija(int ind) throws JSONException {
        return listMund.getJSONObject(ind).getString("nameVideo");
    }

    private void initJSON() throws JSONException{
        listMund = new JSONArray();
        listMund = getAllnameMundarija();
    }

    public void delbase(){
        File file = new File(getPathDataBase() + getDataBaseName());
        if(file.delete()){
            Log.d(TAG, "del : " );
        }
    }

    public long downloadFile(int ind) throws JSONException {

        String link = getLinkMundarija(ind);
        String name = getnameVideoMundarija(ind);
      //  String name = "download";
        String path = getMyVideo();


        DownloadManager dm = (DownloadManager) context.getSystemService(Activity.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(android.net.Uri.parse(link));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle("Downloading");
        request.setDescription("Downloading File");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION);

        request.setDestinationInExternalFilesDir(context, path, name);


        // request.allowScanningByMediaScanner();
        try {
            return dm.enqueue(request);
        }catch (NullPointerException e){
            Log.d(TAG, "NullPointerException : " + e);
        }
        return 0;
    }

    private String getPathDataBase() {
        return pathDataBase;
    }

    public String getPathMyVideo() {
        return pathMyVideo;
    }

    private JSONArray getListMund() {
        return listMund;
    }

    public String getMyVideo() {
        return "/MyVideo";
    }

    private String getDataBase() {
        return "/DataBase";
    }

    private String getDataBaseName() {
        return "/database.db";
    }
}
