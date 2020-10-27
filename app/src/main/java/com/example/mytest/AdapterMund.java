package com.example.mytest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;


public class AdapterMund extends BaseAdapter {

    private Context context;
    private MainActivity mainActivity;
    private FileManager fm;
    private LayoutInflater lInflater;

    private static final String TAG = "myLogs";

    public AdapterMund(Context context) {
        this.context = context;
        fm = new FileManager(context);
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public AdapterMund(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        fm = new FileManager(mainActivity.getApplicationContext());
        lInflater = (LayoutInflater) mainActivity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return fm.getCountBase();
    }

    @Override
    public Object getItem(int position) {

        try {
            return fm.getItemBase(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null){
            view = lInflater.inflate(R.layout.item_mund, parent, false);
        }

        TextView textView = (TextView) view.findViewById(R.id.textmund_id);

        TextView textView3= (TextView) view.findViewById(R.id.textmund2);

        final ImageButton imgbtn = (ImageButton) view.findViewById(R.id.imgbtn3);

        try {
            final JSONObject obj = fm.getItemBase(position);

            textView.setText((position+1)+"-§ " + obj.getString("name"));

            String path = obj.getString("pathVideo");

            if (path.isEmpty()){
                imgbtn.setVisibility(View.VISIBLE);
                textView3.setText(obj.getString("sizeVideo"));

                textView.setBackgroundResource(R.color.btnButtonNormal);

                imgbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            long Id = fm.downloadFile(position);
                            if (Id > 0) {
                                mainActivity.getListIdDownload().put(Id, obj.getString("nameVideo"));
                                imgbtn.setEnabled(false);
                                Toast.makeText(mainActivity.getApplicationContext(), "Загрузка началась", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                imgbtn.setVisibility(View.INVISIBLE);
                textView3.setText("");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }
}
