package com.example.jan.gps_koordinate;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class RezultatiArrayAdapter extends ArrayAdapter {
    Context context;
    int layout;
    String[] fileNames;

    public RezultatiArrayAdapter(@NonNull Context context, int resource, @NonNull Object[] objects) {
        super(context, resource, objects);

        this.context = context;
        layout = resource;
        fileNames = (String[]) objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, layout, null);
        }

        String[] fileName = fileNames[position].split(" ");

        if(fileName.length >= 3){
            TextView datum = convertView.findViewById(R.id.text_datum);
            datum.setText(fileName[0] + " " + fileName[1]);


            TextView procenti = convertView.findViewById(R.id.text_procenti);
            procenti.setText(fileName[2]);
            String temp=fileName[2].substring(0,fileName[2].length()-1);
            int procen=Integer.parseInt(temp);
            if(procen<50) {
                procenti.setTextColor(Color.RED);
            } else if (procen<70){
                procenti.setTextColor(Color.rgb(255,165,0));
            } else {
                procenti.setTextColor(Color.GREEN);
            }

        }
        convertView.setTag(fileNames[position]);
        return convertView;
    }
}
