package com.example.jan.gps_koordinate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
        }

        convertView.setTag(fileNames[position]);
        return convertView;
    }
}
