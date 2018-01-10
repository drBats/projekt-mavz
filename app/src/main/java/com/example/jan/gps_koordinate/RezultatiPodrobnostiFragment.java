package com.example.jan.gps_koordinate;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class RezultatiPodrobnostiFragment extends Fragment {
    String fileName;
    RezultatiKviza rezultatiKviza;

    @Override
    public void setArguments(Bundle args) {
        fileName = args.getString("fileName");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rezultati_podrobnosti, container, false);

        try{
            FileInputStream inputStream = new FileInputStream(getActivity().getFilesDir().getPath() + File.separator + fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            rezultatiKviza = (RezultatiKviza) objectInputStream.readObject();

        } catch (Exception ex){
            Log.e("File", ex.getMessage());
            return rootView;
        }

        ArrayList<Odgovor> odgovori = rezultatiKviza.getOdgovori();

        for(int i = 0; i < odgovori.size(); i++){
            Vprasanje vprasanje = odgovori.get(i).getVprasanje();

            View view = inflater.inflate(R.layout.layout_odgovor, null);
            ((TextView) view.findViewById(R.id.vprasanje)).setText(i+1+". "+vprasanje.toString());
            ((TextView) view.findViewById(R.id.odgovor_a)).setText(vprasanje.getOdgovori().get("a"));
            if((odgovori.get(i).getVprasanje().getPravOdgovor().equals("a")))
            {
                ((TextView) view.findViewById(R.id.odgovor_a)).setTextColor(Color.GREEN);
            }
            else if(odgovori.get(i).getOdgovor().equals("a"))
            {
                ((TextView) view.findViewById(R.id.odgovor_a)).setTextColor(Color.RED);
            }
            ((TextView) view.findViewById(R.id.odgovor_b)).setText(vprasanje.getOdgovori().get("b"));
            if((odgovori.get(i).getVprasanje().getPravOdgovor().equals("b")))
            {
                ((TextView) view.findViewById(R.id.odgovor_b)).setTextColor(Color.GREEN);
            }
            else if(odgovori.get(i).getOdgovor().equals("b"))
            {
                ((TextView) view.findViewById(R.id.odgovor_b)).setTextColor(Color.RED);
            }
            ((TextView) view.findViewById(R.id.odgovor_c)).setText(vprasanje.getOdgovori().get("c"));
            if((odgovori.get(i).getVprasanje().getPravOdgovor().equals("c")))
            {
                ((TextView) view.findViewById(R.id.odgovor_c)).setTextColor(Color.GREEN);
            }
            else if(odgovori.get(i).getOdgovor().equals("c"))
            {
                ((TextView) view.findViewById(R.id.odgovor_c)).setTextColor(Color.RED);
            }

            ((LinearLayout) rootView.findViewById(R.id.odgovori)).addView(view);
        }

        return rootView;
    }
}
